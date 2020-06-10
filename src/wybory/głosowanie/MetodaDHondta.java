package wybory.głosowanie;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.partia.Partia;
import wybory.pomoce.para.Para;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MetodaDHondta extends MetodaLiczeniaGłosów {

    @Override
    public List<Para<Partia, Integer>> obliczPrzydziałMandatówWOkręgu
            (OkręgWyborczy okręg, Set<Map.Entry<Kandydat, Integer>> liczbaGłosów) {

        //@todo Tu Skończyłem
        return null;
    }
}
