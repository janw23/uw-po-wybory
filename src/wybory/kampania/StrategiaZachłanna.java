package wybory.kampania;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.osoba.wyborca.Jednopartyjny;
import wybory.osoba.wyborca.Wszechstronny;
import wybory.osoba.wyborca.Wyborca;
import wybory.partia.Partia;
import wybory.pomoce.Pomoce;
import wybory.pomoce.Wartościowanie;
import wybory.pomoce.para.Para;
import wybory.pomoce.wektor.WektorOgraniczony;

import java.util.ArrayList;
import java.util.List;

public class StrategiaZachłanna extends StrategiaKampanii {

    private boolean wszechstronnyIMożeGłosowaćNaPartię(Wyborca wyborca, Partia partia) {
        if (wyborca instanceof Jednopartyjny) {
            if (!((Jednopartyjny) wyborca).uwielbionaPartia().equals(partia))
                return false;
        }
        return wyborca instanceof Wszechstronny;
    }

    private List<Wszechstronny> wyborcyWszechstronniMogącyGłosowaćNaPartię(OkręgWyborczy okręg,
                                                                           Partia partia) {
        List<Wszechstronny> wyborcy = new ArrayList<>();

        for (Wyborca wyborca : okręg.wyborcy(true))
            if (wszechstronnyIMożeGłosowaćNaPartię(wyborca, partia))
                wyborcy.add((Wszechstronny) wyborca);

        return wyborcy;
    }

    //liczy sumę sum ważonych wyborców i kandydatów po wykonaniu działania
    private int sumaSumWażonych(DziałanieKampanijne działanie,
                                List<Kandydat> kandydaci, List<Wszechstronny> wyborcy) {
        int suma = 0;

        //liczy sumę ważoną, na końcu przywracając początkowe wagi wyborcy
        for (Wszechstronny wyborca : wyborcy) {
            WektorOgraniczony stareWagi = new WektorOgraniczony(wyborca.wagiCech());
            działanie.wykonajNa(wyborca);

            for (Kandydat kandydat : kandydaci)
                suma += wyborca.sumaWażonaCech(kandydat);

            wyborca.wagiCech(stareWagi);
        }

        return suma;
    }

    @Override
    public Para<DziałanieKampanijne, OkręgWyborczy> wybierzNajlepszeDziałanieKampanijne
            (DaneKampanii daneKampanii, Partia partia, int pozostałyBudżet) {

        //trójka (suma sum ważonych, działanie, okręg)
        List<Para<Integer, Para<DziałanieKampanijne, OkręgWyborczy>>> możliwości = new ArrayList<>();

        //tworzy trójki możliwych działań (suma sum ważonych, działanie, okręg)
        for (OkręgWyborczy okręg : daneKampanii.okręgiWyborcze()) {
            List<Kandydat> kandydaciPartii = partia.kandydaciWOkręgu(okręg);
            List<Wszechstronny> wyborcy = wyborcyWszechstronniMogącyGłosowaćNaPartię(okręg, partia);

            for (DziałanieKampanijne działanie : daneKampanii.działaniaKampanijne()) {
                if (działanie.obliczKosztWykonania(okręg) <= pozostałyBudżet) {
                    int sumaSumWażonych = sumaSumWażonych(działanie, kandydaciPartii, wyborcy);
                    możliwości.add(new Para<>(sumaSumWażonych, new Para<>(działanie, okręg)));
                }
            }
        }

        //wybieranie możliwości o największej sumie sum ważonych
        Wartościowanie<Para<Integer, Para<DziałanieKampanijne, OkręgWyborczy>>> wartościowanie =
                new Wartościowanie<>() {
                    @Override
                    public int wartość(Para<Integer, Para<DziałanieKampanijne, OkręgWyborczy>> o) {
                        return o.pierwszy();
                    }
                };

        var najlepszy = Pomoce.wybierzNajlepszyLosowy(możliwości, wartościowanie);

        return najlepszy == null ? null : najlepszy.drugi();
    }
}
