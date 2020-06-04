package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;

public class MaksymalizującyJednocechowopartyjny extends Wyborca implements Jednopartyjny, Jednocechowy {
    public MaksymalizującyJednocechowopartyjny(String imię, String nazwisko, OkręgWyborczy okręgWyborczy, int typ) {
        super(imię, nazwisko, okręgWyborczy, typ);
    }
}
