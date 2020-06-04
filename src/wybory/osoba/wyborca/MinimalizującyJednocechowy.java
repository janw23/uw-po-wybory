package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;

public class MinimalizującyJednocechowy extends Wyborca implements Jednocechowy {
    public MinimalizującyJednocechowy(String imię, String nazwisko, OkręgWyborczy okręgWyborczy, int typ) {
        super(imię, nazwisko, okręgWyborczy, typ);
    }
}
