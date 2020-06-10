package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.pomoce.wektor.Wektor;
import wybory.pomoce.wektor.WektorOgraniczony;

public class Wszechstronny extends Wyborca {

    private final WektorOgraniczony wagiCech;

    public Wszechstronny(String imię, String nazwisko,
                         OkręgWyborczy okręgWyborczy,
                         WektorOgraniczony wagiCech) {

        super(imię, nazwisko, okręgWyborczy);
        this.wagiCech = wagiCech;
    }

    public void zmieńWagiCechOWektor(Wektor wektor) {
        assert wektor.liczbaWspółrzędnych() == wagiCech.liczbaWspółrzędnych();
        wagiCech.dodaj(wektor);
    }

    public int sumaWażonaCech(Kandydat kandydat) {
        return wagiCech.iloczynSkalarny(kandydat.cechy());
    }
}
