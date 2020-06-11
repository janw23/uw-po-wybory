package wybory.głosowanie;

import wybory.strukturyWyborcze.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.strukturyWyborcze.Partia;
import wybory.pomoce.para.Para;

import java.util.*;

public abstract class MetodaNajwiększychIlorazów extends MetodaLiczeniaGłosów {

    @Override
    public final Map<Partia, Integer> obliczPrzydziałMandatówWOkręgu(OkręgWyborczy okręg,
                                                                     Map<Kandydat, Integer> liczbaGłosów) {
        assert tylkoKandydaciZOkręgu(okręg, liczbaGłosów);
        Map<Partia, Integer> głosyUzyskanePrzezPartie =
                przeliczGłosyUzyskanePrzezPartie(liczbaGłosów);

        int liczbaMandatów = okręg.liczbaMandatów(true);
        int liczbaPartii = głosyUzyskanePrzezPartie.size();

        List<Para<Partia, Float>> ilorazy = new ArrayList<>(liczbaMandatów * liczbaPartii);

        for (Partia partia : głosyUzyskanePrzezPartie.keySet()) {
            int liczbaGłosówNaPartię = głosyUzyskanePrzezPartie.get(partia);

            for (int i = 1; i <= liczbaMandatów; i++)
                ilorazy.add(new Para<>(partia, obliczIlorazPrzyjętąMetodą(liczbaGłosówNaPartię, i)));
        }

        //losowość w rozstrzyganiu remisów
        Collections.shuffle(ilorazy);

        Comparator<Para<Partia, Float>> porządekIlorazów = (a, b) -> porównajIlorazy(a.drugi(), b.drugi());
        ilorazy.sort(porządekIlorazów);

        Map<Partia, Integer> przydziałMandatów = new HashMap<>();

        for (int i = 0; i < liczbaMandatów; i++) {
            Partia partia = ilorazy.get(i).pierwszy();
            przydziałMandatów.merge(partia, 1, Integer::sum);
        }
        return przydziałMandatów;
    }

    //numery ilorazów sa indeksowane od 1
    abstract float obliczIlorazPrzyjętąMetodą(int liczbaGłosówNaPartię, int numerIlorazu);

    int porównajIlorazy(float a, float b) {
        //standardowe porównywanie ilorazów w porządku malejącym
        return -Float.compare(a, b);
    }

    private boolean tylkoKandydaciZOkręgu(OkręgWyborczy okręg, Map<Kandydat, Integer> głosyKandydatów) {
        for (Kandydat kandydat : głosyKandydatów.keySet()) {
            if (!kandydat.okręgWyborczy(true).equals(okręg))
                return false;
        }
        return true;
    }

}
