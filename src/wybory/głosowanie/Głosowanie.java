package wybory.głosowanie;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.osoba.wyborca.Wyborca;
import wybory.partia.Partia;
import wybory.pomoce.para.Para;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//klasa regulująca zasady i przeprowadzanie głosowanie
public class Głosowanie {

    private final MetodaLiczeniaGłosów metodaLiczeniaGłosów;
    private final List<Para<Wyborca, Kandydat>> głosy; //każdy głos na kandydata jest tu wpisywany

    private final HashMap<OkręgWyborczy, HashMap<Kandydat, Integer>> liczbaGłosówNaKandydatówWOkręgach;
    private final HashMap<Partia, Integer> mandatyUzyskanePrzezPartie;

    public Głosowanie(MetodaLiczeniaGłosów metodaLiczeniaGłosów) {
        this.metodaLiczeniaGłosów = metodaLiczeniaGłosów;
        głosy = new ArrayList<>();  //@todo Lepsze LinkedList???
        liczbaGłosówNaKandydatówWOkręgach = new HashMap<>();
        mandatyUzyskanePrzezPartie = new HashMap<>();
    }

    public void przeprowadźGłosowanieWOkręgach(List<OkręgWyborczy> okręgi) {
        for (OkręgWyborczy okręg : okręgi)
            przeprowadźGłosowanieWOkręgu(okręg);
    }

    private void przeprowadźGłosowanieWOkręgu(OkręgWyborczy okręg) {
        assert okręg.jestGłówny();

        //@todo czy funkcja w definicji pętli wywoływana jest raz czy wielokrotnie?
        for (Wyborca wyborca : okręg.wyborcy(true)) {
            Kandydat wybranyKandydat = wyborca.wybranyKandydat();
            assert ważnyGłos(wybranyKandydat, wyborca);
            głosy.add(new Para<>(wyborca, wybranyKandydat));
        }
    }

    private boolean ważnyGłos(Kandydat wybranyKandydat, Wyborca głosującyWyborca) {
        return wybranyKandydat.okręgWyborczy(true).
                equals(głosującyWyborca.okręgWyborczy(true));
    }

    public void resetujGłosy() {
        głosy.clear();
    }

    public void przeliczGłosy() {
        przeliczGłosyNaKandydatów();
        przeliczGłosyNaMandaty();
    }

    private void przeliczGłosyNaKandydatów() {
        for (Para<Wyborca, Kandydat> głos : głosy) {
            Kandydat kandydat = głos.drugi();
            OkręgWyborczy okręg = kandydat.okręgWyborczy(true);

            //szuka danych głosowania danego okręu, a jeśli ich nie ma, to tworzy nowe
            var liczbaGłosówNaKandydatów =
                    liczbaGłosówNaKandydatówWOkręgach.computeIfAbsent(okręg, k -> new HashMap<>());

            //jeśli w mapie nie ma jeszcze kandydata, to wstawia go z liczbą głosów 0
            liczbaGłosówNaKandydatów.putIfAbsent(kandydat, 0);
            liczbaGłosówNaKandydatów.computeIfPresent(kandydat, (k, v) -> v + 1);
        }
    }

    //przed wywołaniem tej funkcji głosy muszą być przeliczone
    public int liczbaGłosówNaKandydata(Kandydat kandydat) {
        OkręgWyborczy okręg = kandydat.okręgWyborczy(true);
        var głosyWOkręgu = liczbaGłosówNaKandydatówWOkręgach.get(okręg);

        if (głosyWOkręgu == null)
            return 0;

        return głosyWOkręgu.getOrDefault(kandydat, 0);
    }

    private void przeliczGłosyNaMandaty() {
        for (var daneOkręgu : liczbaGłosówNaKandydatówWOkręgach.entrySet()) {
            var okręg = daneOkręgu.getKey();
            var głosyWOkręgu = daneOkręgu.getValue().entrySet();
            var mandatyPartii = metodaLiczeniaGłosów.obliczPrzydziałMandatówWOkręgu(okręg, głosyWOkręgu);

            for (var mandaty : mandatyPartii) {
                Partia partia = mandaty.pierwszy();
                int liczbaMandatów = mandaty.drugi();
                mandatyUzyskanePrzezPartie.putIfAbsent(partia, liczbaMandatów);
                mandatyUzyskanePrzezPartie.computeIfPresent(partia, (k, v) -> v + liczbaMandatów);
            }
        }
    }

    //wcześniej musi byc wywołane przelicz głosy na mandaty
    public int liczbaMandatówUzyskanychPrzezPartię(Partia partia) {
        return mandatyUzyskanePrzezPartie.getOrDefault(partia, 0);
    }
}
