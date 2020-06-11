package wybory.głosowanie;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.osoba.wyborca.Wyborca;
import wybory.partia.Partia;
import wybory.pomoce.para.Para;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//klasa regulująca zasady i przeprowadzanie głosowanie
public class Głosowanie {

    private final MetodaLiczeniaGłosów metodaLiczeniaGłosów;
    private final List<Para<Wyborca, Kandydat>> głosy; //każdy głos na kandydata jest tu wpisywany

    private final Map<OkręgWyborczy, HashMap<Kandydat, Integer>> liczbaGłosówNaKandydatówWOkręgach;
    private final Map<Partia, Integer> mandatyUzyskanePrzezPartie;

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

    public void resetujGłosowanie() {
        głosy.clear();
        liczbaGłosówNaKandydatówWOkręgach.clear();
        mandatyUzyskanePrzezPartie.clear();
    }

    public void przeliczGłosy() {
        przeliczGłosyNaKandydatów();
        assert zgodnaLiczbaGłosów();
        przeliczGłosyNaMandaty();
    }

    //funkcja do upewnienia się, że wszystkie głosy zostały policzone
    private boolean zgodnaLiczbaGłosów() {
        for (var głosyWOkręgu : liczbaGłosówNaKandydatówWOkręgach.entrySet()) {
            int suma = 0;

            for (int liczbaGłosów : głosyWOkręgu.getValue().values())
                suma += liczbaGłosów;

            if (suma != głosyWOkręgu.getKey().wyborcy(true).size())
                return false;
        }

        return true;
    }

    private void przeliczGłosyNaKandydatów() {
        for (Para<Wyborca, Kandydat> głos : głosy) {
            Kandydat kandydat = głos.drugi();
            OkręgWyborczy okręg = kandydat.okręgWyborczy(true);

            //szuka danych głosowania danego okręu, a jeśli ich nie ma, to tworzy nowe
            var liczbaGłosówNaKandydatów =
                    liczbaGłosówNaKandydatówWOkręgach.computeIfAbsent(okręg, k -> new HashMap<>());

            liczbaGłosówNaKandydatów.merge(kandydat, 1, Integer::sum);
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

            var mandatyPartii = metodaLiczeniaGłosów
                    .obliczPrzydziałMandatówWOkręgu(daneOkręgu.getKey(), daneOkręgu.getValue());

            for (var mandaty : mandatyPartii.entrySet())
                mandatyUzyskanePrzezPartie.merge(mandaty.getKey(), mandaty.getValue(), Integer::sum);
        }
    }

    //wcześniej musi byc wywołane przelicz głosy na mandaty
    public int liczbaMandatówUzyskanychPrzezPartię(Partia partia) {
        return mandatyUzyskanePrzezPartie.getOrDefault(partia, 0);
    }
}
