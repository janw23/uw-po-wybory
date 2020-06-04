package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;

public class WszechstronnyJednopartyjny extends Wyborca implements Jednopartyjny {
    public WszechstronnyJednopartyjny(String imię, String nazwisko, OkręgWyborczy okręgWyborczy, int typ) {
        super(imię, nazwisko, okręgWyborczy, typ);
    }
}
