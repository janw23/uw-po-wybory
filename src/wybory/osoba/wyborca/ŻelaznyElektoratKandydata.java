package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;

public class ŻelaznyElektoratKandydata
        extends Wyborca implements Jednokandydatowy {

    private final Kandydat uwielbionykandydat;

    public ŻelaznyElektoratKandydata
            (String imię, String nazwisko,
             OkręgWyborczy okręgWyborczy, Kandydat uwielbionyKandydat) {

        super(imię, nazwisko, okręgWyborczy);
        this.uwielbionykandydat = uwielbionyKandydat;
    }
}
