package wybory.kampania;

import wybory.strukturyWyborcze.OkręgWyborczy;
import wybory.osoba.wyborca.Wszechstronny;
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
        for (Wyborca wyborca : okręg.wyborcy(true))
            if (wyborca instanceof Wszechstronny)
                wykonajNa((Wszechstronny) wyborca);
    }

    public void wykonajNa(Wszechstronny wyborca) {
        wyborca.zmieńWagiCechOWektor(zmianyWag);
    }
}
