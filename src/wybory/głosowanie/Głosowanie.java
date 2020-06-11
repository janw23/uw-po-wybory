package wybory.głosowanie;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.osoba.wyborca.Wyborca;
import wybory.partia.Partia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//klasa regulująca zasady i przeprowadzanie głosowanie
public class Głosowanie {

    private final MetodaLiczeniaGłosów metodaLiczeniaGłosów;

    private final Map<Wyborca, Kandydat> wybraniKandydaciWyborców; //każdy głos na kandydata jest tu wpisywany
    private final Map<OkręgWyborczy, Map<Kandydat, Integer>> liczbaGłosówNaKandydatówWOkręgach;
    private final Map<OkręgWyborczy, Map<Partia, Integer>> mandatyUzyskanePrzezPartieWOkręgach;
    private final Map<Partia, Integer> mandatyUzyskanePrzezPartie;

    public Głosowanie(MetodaLiczeniaGłosów metodaLiczeniaGłosów) {
        this.metodaLiczeniaGłosów = metodaLiczeniaGłosów;
        wybraniKandydaciWyborców = new HashMap<>();  //@todo Lepsze LinkedList???
        liczbaGłosówNaKandydatówWOkręgach = new HashMap<>();
        mandatyUzyskanePrzezPartie = new HashMap<>();
        mandatyUzyskanePrzezPartieWOkręgach = new HashMap<>();
    }

    public MetodaLiczeniaGłosów metodaLiczeniaGłosów() {
        return metodaLiczeniaGłosów;
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
            boolean temp =!wybraniKandydaciWyborców.containsKey(wyborca);
            assert !wybraniKandydaciWyborców.containsKey(wyborca);

            wybraniKandydaciWyborców.put(wyborca, wybranyKandydat);
        }
    }

    private boolean ważnyGłos(Kandydat wybranyKandydat, Wyborca głosującyWyborca) {
        return wybranyKandydat.okręgWyborczy(true).
                equals(głosującyWyborca.okręgWyborczy(true));
    }

    public void resetujGłosowanie() {
        wybraniKandydaciWyborców.clear();
        liczbaGłosówNaKandydatówWOkręgach.clear();
        mandatyUzyskanePrzezPartie.clear();
        wybraniKandydaciWyborców.clear();
        mandatyUzyskanePrzezPartieWOkręgach.clear();
    }

    public void przeliczGłosy() {
        przeliczGłosyNaKandydatów();
        assert zgodnaLiczbaGłosów();
        przeliczGłosyNaMandatyWOkręgach();
        //@todo Dopisać asercję uzyskanych mandatów
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
        for (var głosWyborcy : wybraniKandydaciWyborców.entrySet()) {
            Kandydat kandydat = głosWyborcy.getValue();
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

    public Kandydat wybranyKandydatWyborcy(Wyborca wyborca) {
        return wybraniKandydaciWyborców.get(wyborca);
    }

    private void przeliczGłosyNaMandatyWOkręgach() {
        for (var daneOkręgu : liczbaGłosówNaKandydatówWOkręgach.entrySet()) {
            OkręgWyborczy okręg = daneOkręgu.getKey();

            var mandatyPartii = metodaLiczeniaGłosów
                    .obliczPrzydziałMandatówWOkręgu(okręg, daneOkręgu.getValue());

            assert !mandatyUzyskanePrzezPartieWOkręgach.containsKey(okręg);
            mandatyUzyskanePrzezPartieWOkręgach.put(okręg, mandatyPartii);

            //dodawanie mandatów do wszystkich mandatów danej partii
            for (var mandaty : mandatyPartii.entrySet())
                mandatyUzyskanePrzezPartie.merge(mandaty.getKey(), mandaty.getValue(), Integer::sum);
        }
    }

    //wcześniej musi byc wywołane przelicz głosy na mandaty
    public int liczbaMandatówUzyskanychPrzezPartię(Partia partia) {
        return mandatyUzyskanePrzezPartie.getOrDefault(partia, 0);
    }

    public int liczbaMandatówUzyskanychPrzezPartięWOkręgu(Partia partia, OkręgWyborczy okręg) {
        var mandatyWOkręgu = mandatyUzyskanePrzezPartieWOkręgach.get(okręg);
        assert mandatyWOkręgu != null;
        return mandatyWOkręgu.getOrDefault(partia, 0);
    }
}
