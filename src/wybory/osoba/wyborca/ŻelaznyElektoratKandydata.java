package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;

public class ŻelaznyElektoratKandydata extends Wyborca implements Jednokandydatowy {
    public ŻelaznyElektoratKandydata(String imię, String nazwisko, OkręgWyborczy okręgWyborczy, int typ) {
        super(imię, nazwisko, okręgWyborczy, typ);
    }
}
