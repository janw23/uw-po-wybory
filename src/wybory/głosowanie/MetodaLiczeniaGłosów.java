package wybory.głosowanie;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.partia.Partia;

import java.util.HashMap;
import java.util.Map;

public abstract class MetodaLiczeniaGłosów {

    public abstract Map<Partia, Integer> obliczPrzydziałMandatówWOkręgu
            (OkręgWyborczy okręg, Map<Kandydat, Integer> liczbaGłosów);

    Map<Partia, Integer> przeliczGłosyUzyskanePrzezPartie(Map<Kandydat, Integer> głosyKandydatów) {
        Map<Partia, Integer> liczbaGłosówNaPartie = new HashMap<>(); //(partia -> liczba głosów)

        for (var głos : głosyKandydatów.entrySet())
            liczbaGłosówNaPartie.merge(głos.getKey().partia(), głos.getValue(), Integer::sum);

        assert zgodnaLiczbaGłosów(głosyKandydatów, liczbaGłosówNaPartie);
        return liczbaGłosówNaPartie;
    }

    public abstract String nazwa();

    private boolean zgodnaLiczbaGłosów(Map<Kandydat, Integer> głosyKandydatów, Map<Partia, Integer> głosyPartii) {
        int suma = 0;

        for (var głosy : głosyKandydatów.entrySet())
            suma += głosy.getValue();

        for (var głosy : głosyPartii.values())
            suma -= głosy;

        return suma == 0;
    }
}
