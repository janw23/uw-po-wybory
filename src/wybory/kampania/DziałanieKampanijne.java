package wybory.kampania;

import wybory.OkręgWyborczy;
import wybory.osoba.wyborca.Wyborca;
import wybory.pomoce.wektor.WektorOgraniczony;

public class DziałanieKampanijne {

    private final WektorOgraniczony zmianyWag;
    private final int kosztBazowy;

    public DziałanieKampanijne(WektorOgraniczony zmianyWag) {
        this.zmianyWag = zmianyWag;
        this.kosztBazowy = obliczKosztBazowy();
    }

    private int obliczKosztBazowy() {
        int sumaBezwzględna = 0;

        for (int i = 0; i < zmianyWag.liczbaWspółrzędnych(); i++)
            sumaBezwzględna += Math.abs(zmianyWag.dajWspółrzędną(i));

        return sumaBezwzględna;
    }

    public int obliczKosztWykonania(OkręgWyborczy okręgWyborczy) {
        return kosztBazowy * okręgWyborczy.liczbaWyborców(true);
    }

    public void wykonajNa(OkręgWyborczy okręg) {
        for (Wyborca wyborca : okręg.wyborcy())
            if (wyborca instanceof WażącyCechy)
                ((WażącyCechy) wyborca).zmieńWagiCechOWektor(zmianyWag);
    }
}
