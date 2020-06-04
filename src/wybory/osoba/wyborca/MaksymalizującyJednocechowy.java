package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;

public class MaksymalizującyJednocechowy extends Wyborca implements Jednocechowy {
    public MaksymalizującyJednocechowy(String imię, String nazwisko, OkręgWyborczy okręgWyborczy, int typ) {
        super(imię, nazwisko, okręgWyborczy, typ);
    }
}
