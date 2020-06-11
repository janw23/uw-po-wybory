package wybory.głosowanie;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.partia.Partia;
import wybory.pomoce.para.Para;

import java.util.*;

public class MetodaHareaNiemeyera extends MetodaLiczeniaGłosów {

    @Override
    public final String nazwa() {
        return "Metoda Hare’a-Niemeyera";
    }

    @Override
    public Map<Partia, Integer> obliczPrzydziałMandatówWOkręgu(OkręgWyborczy okręg,
                                                               Map<Kandydat, Integer> liczbaGłosów) {
        Map<Partia, Integer> głosyUzyskanePrzezPartie =
                przeliczGłosyUzyskanePrzezPartie(liczbaGłosów);

        int liczbaPartii = głosyUzyskanePrzezPartie.size();
        int łącznaLiczbaGłosów = łącznaLiczbaGłosów(głosyUzyskanePrzezPartie);
        int liczbaMandatów = okręg.liczbaMandatów(true);

        List<Para<Partia, Float>> ilorazy = new ArrayList<>(liczbaPartii);

        //liczenie ilorazów
        for (var głosyNaPartię : głosyUzyskanePrzezPartie.entrySet()) {
            float iloraz = (float) głosyNaPartię.getValue() * liczbaMandatów / łącznaLiczbaGłosów;
            ilorazy.add(new Para<>(głosyNaPartię.getKey(), iloraz));
        }

        Map<Partia, Integer> przydziałMandatów = new HashMap<>();
        int przydzieloneMandaty = 0;

        for (var iloraz : ilorazy) {
            int uzyskaneMandaty = iloraz.drugi().intValue();
            przydzieloneMandaty += uzyskaneMandaty;
            przydziałMandatów.put(iloraz.pierwszy(), uzyskaneMandaty);
        }
        assert przydzieloneMandaty <= liczbaMandatów;

        //jeśli nie zostały rozdane wszystkie mandaty trzeba zdecydować patrząc na reszty ilorazów
        if (przydzieloneMandaty < liczbaMandatów) {
            Collections.shuffle(ilorazy);
            ilorazy.sort((a, b) -> -Float.compare(częśćUłamkowa(a.drugi()), częśćUłamkowa(b.drugi())));

            for (var iloraz : ilorazy) {
                if (przydzieloneMandaty < liczbaMandatów) {
                    przydziałMandatów.merge(iloraz.pierwszy(), 1, Integer::sum);
                    przydzieloneMandaty++;
                } else break;
            }
        }

        assert przydzieloneMandaty == liczbaMandatów;
        return przydziałMandatów;
    }

    private int łącznaLiczbaGłosów(Map<Partia, Integer> głosyUzyskanePrzezPartie) {
        int suma = 0;

        for (var głosy : głosyUzyskanePrzezPartie.values())
            suma += głosy;

        return suma;
    }

    private float częśćUłamkowa(Float a) {
        return a - a.intValue();
    }
}
