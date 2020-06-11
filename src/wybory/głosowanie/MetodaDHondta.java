package wybory.głosowanie;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.partia.Partia;
import wybory.pomoce.para.Para;

import java.util.*;

public class MetodaDHondta extends MetodaLiczeniaGłosów {

    @Override
    public Map<Partia, Integer> obliczPrzydziałMandatówWOkręgu
            (OkręgWyborczy okręg, Map<Kandydat, Integer> głosy) {

        assert tylkoKandydaciZOkręgu(okręg, głosy);
        Map<Partia, Integer> głosyUzyskanePrzezPartie = przeliczGłosyUzyskanePrzezPartie(głosy);

        int liczbaMandatów = okręg.liczbaMandatów(true);
        int liczbaPartii = głosyUzyskanePrzezPartie.size();

        List<Para<Partia, Float>> ilorazy = new ArrayList<>(liczbaMandatów * liczbaPartii);

        for (Partia partia : głosyUzyskanePrzezPartie.keySet()) {
            int liczbaGłosówNaPartię = głosyUzyskanePrzezPartie.get(partia);

            for (int i = 1; i <= liczbaMandatów; i++)
                ilorazy.add(new Para<>(partia, (float) liczbaGłosówNaPartię / i));
        }

        //losowość w rozstrzyganiu remisów
        Collections.shuffle(ilorazy);

        Comparator<Para<Partia, Float>> ilorazyMalejąco = (a, b) -> -Float.compare(a.drugi(), b.drugi());
        ilorazy.sort(ilorazyMalejąco);

        Map<Partia, Integer> przydziałMandatów = new HashMap<>();

        for (int i = 0; i < liczbaMandatów; i++) {
            Partia partia = ilorazy.get(i).pierwszy();
            przydziałMandatów.merge(partia, 1, Integer::sum);
        }

        return przydziałMandatów;
    }

    private boolean tylkoKandydaciZOkręgu(OkręgWyborczy okręg, Map<Kandydat, Integer> głosyKandydatów) {
        for (Kandydat kandydat : głosyKandydatów.keySet()) {
            if (!kandydat.okręgWyborczy(true).equals(okręg))
                return false;
        }
        return true;
    }
}
