package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.pomoce.wektor.WektorOgraniczony;

public class Wszechstronny extends Wyborca {

    private final WektorOgraniczony wagiCech;

    public Wszechstronny(String imię, String nazwisko,
                         OkręgWyborczy okręgWyborczy,
                         WektorOgraniczony wagiCech) {

        super(imię, nazwisko, okręgWyborczy);
        this.wagiCech = wagiCech;
    }
}
