package wybory.kampania;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.osoba.wyborca.Jednopartyjny;
import wybory.osoba.wyborca.Wszechstronny;
import wybory.osoba.wyborca.Wyborca;
import wybory.partia.Partia;
import wybory.pomoce.para.Para;

import java.util.ArrayList;
import java.util.List;

public class StrategiaZachłanna extends StrategiaKampanii {

    private boolean ważącyCechyIMożeGłosowaćNaPartię(Wyborca wyborca, Partia partia) {
        if (wyborca instanceof Jednopartyjny) {
            if (!((Jednopartyjny) wyborca).uwielbionaPartia().equals(partia))
                return false;
        }
        return wyborca instanceof WażącyCechy;
    }

    private List<Wszechstronny> wyborcyWażącyCechyMogącyGłosowaćNaPartię(OkręgWyborczy okręg, Partia partia) {
        List<Wszechstronny> wyborcy = new ArrayList<>();

        for (Wyborca wyborca : okręg.wyborcy())
            if (ważącyCechyIMożeGłosowaćNaPartię(wyborca, partia))
                wyborcy.add((Wszechstronny) wyborca);

        return wyborcy;
    }

    //liczy sumę sum ważonych wyborców i kandydatów po wykonaniu działania
    private int sumaSumWażonych(DziałanieKampanijne działanie,
                                           List<Kandydat> kandydaci, List<Wszechstronny> wyborcy) {

        int suma = 0;
        for (Wszechstronny wyborca : wyborcy)
            //@todo Tu Skończyłem

    }

    @Override
    public Para<DziałanieKampanijne, OkręgWyborczy> wybierzNajlepszeDziałanieKampanijne
            (DaneKampanii daneKampanii, Partia partia, int pozostałyBudżet) {

        for (OkręgWyborczy okręg : daneKampanii.okręgiWyborcze()) {
            List<Kandydat> kandydaciPartii = partia.kandydaciWOkręgu(okręg);
            List<Wszechstronny> wyborcy = wyborcyWażącyCechyMogącyGłosowaćNaPartię(okręg, partia);

            for (DziałanieKampanijne działanie : daneKampanii.działaniaKampanijne()) {
                if (działanie.obliczKosztWykonania(okręg) > pozostałyBudżet)
                    continue;




            }

        }

        return null;
    }
}
