package wybory.kampania;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.osoba.wyborca.Jednopartyjny;
import wybory.osoba.wyborca.Wszechstronny;
import wybory.partia.Partia;
import wybory.pomoce.para.Para;
import wybory.pomoce.wektor.Wektor;
import wybory.pomoce.wektor.WektorOgraniczony;

import java.util.ArrayList;
import java.util.List;


public class StrategiaWłasna extends StrategiaKampanii {

    private boolean możeGłosowaćNaPartię(Wszechstronny wyborca, Partia partia) {
        if (wyborca instanceof Jednopartyjny)
            return ((Jednopartyjny) wyborca).uwielbionaPartia().equals(partia);

        return true;
    }

    private int sumaBezwzględnaWag(Wektor wagi) {
        int suma = 0;

        for (int i = 0; i < wagi.liczbaWspółrzędnych(); i++)
            suma += Math.abs(wagi.dajWspółrzędną(i));

        return suma;
    }

    private float zmianaOdległościOdZeraWagCech(DziałanieKampanijne działanie,
                                              List<Wszechstronny> wyborcy) {
        float sumaOdległościOdZera = 0;
        float sumaOdległościOdZeraPoZmianie = 0;

        for (Wszechstronny wyborca : wyborcy) {
            WektorOgraniczony stareWagi = new WektorOgraniczony(wyborca.wagiCech());

            sumaOdległościOdZera += (float) sumaBezwzględnaWag(wyborca.wagiCech()) / wyborca.wagiCech().liczbaWspółrzędnych();

            działanie.wykonajNa(wyborca);
            sumaOdległościOdZeraPoZmianie += (float) sumaBezwzględnaWag(wyborca.wagiCech()) / wyborca.wagiCech().liczbaWspółrzędnych();

            wyborca.wagiCech(stareWagi);
        }

        return sumaOdległościOdZeraPoZmianie - sumaOdległościOdZera;
    }

    @Override
    public Para<DziałanieKampanijne, OkręgWyborczy> wybierzNajlepszeDziałanieKampanijne
            (DaneKampanii daneKampanii, Partia partia, int pozostałyBudżet) {

        List<Para<Integer, Para<DziałanieKampanijne, OkręgWyborczy>>> możliwości = new ArrayList<>();

        for (OkręgWyborczy okręg : daneKampanii.okręgiWyborcze()) {
            List<Wszechstronny> wyborcyMoogącyGłosowaćNaPartię =
                    wyborcyWszechstronniSpełniającyPredykat(okręg, (w) -> możeGłosowaćNaPartię(w, partia));

            for (DziałanieKampanijne działanie : daneKampanii.działaniaKampanijne()) {
                if (działanie.obliczKosztWykonania(okręg) <= pozostałyBudżet) {
                    float zmianaOdległościOdZeraWagCech =
                            zmianaOdległościOdZeraWagCech(działanie, wyborcyMoogącyGłosowaćNaPartię);

                    int ocenaMożliwości = (int) (10000 * zmianaOdległościOdZeraWagCech);
                    możliwości.add(new Para<>(ocenaMożliwości, new Para<>(działanie, okręg)));
                }
            }
        }

        return możliwośćONajwiększymWartościowaniu(możliwości);
    }
}
