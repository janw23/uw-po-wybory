package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;

public class ŻelaznyElektoratPartyjny extends Wyborca implements Jednopartyjny {
    public ŻelaznyElektoratPartyjny(String imię, String nazwisko, OkręgWyborczy okręgWyborczy, int typ) {
        super(imię, nazwisko, okręgWyborczy, typ);
    }
}
