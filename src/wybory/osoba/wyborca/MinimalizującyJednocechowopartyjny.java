package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;

public class MinimalizującyJednocechowopartyjny extends Wyborca implements Jednocechowy, Jednopartyjny {
    public MinimalizującyJednocechowopartyjny(String imię, String nazwisko, OkręgWyborczy okręgWyborczy, int typ) {
        super(imię, nazwisko, okręgWyborczy, typ);
    }
}
