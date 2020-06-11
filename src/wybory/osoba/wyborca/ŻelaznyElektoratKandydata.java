package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

public class ŻelaznyElektoratKandydata extends Wyborca implements Jednokandydatowy {

    private final Kandydat uwielbionykandydat;

    public ŻelaznyElektoratKandydata
            (String imię, String nazwisko,
             OkręgWyborczy okręgWyborczy, Kandydat uwielbionyKandydat) {

        super(imię, nazwisko, okręgWyborczy);
        this.uwielbionykandydat = uwielbionyKandydat;
    }

    @Override
    public Kandydat uwielbionyKandydat() {
        return uwielbionykandydat;
    }

    @Override
    public Kandydat wybranyKandydat() {
        return uwielbionyKandydat();
    }

    @Override
    List<Kandydat> rozważaniKandydaci() {
        return Collections.singletonList(uwielbionyKandydat());
    }

    @Override
    public Object clone() {
        return new ŻelaznyElektoratKandydata(imię, nazwisko, okręgWyborczy, uwielbionykandydat);
    }
}
