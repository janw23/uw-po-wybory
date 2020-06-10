package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;

import java.util.List;

public abstract class WyborcaJednocechowy extends Wyborca implements Jednocechowy {

    private final int uwielbionaCecha;  //numer uwielbionej cechy

    public WyborcaJednocechowy(String imię, String nazwisko,
                               OkręgWyborczy okręgWyborczy,
                               int uwielbionaCecha) {

        super(imię, nazwisko, okręgWyborczy);
        assert uwielbionaCecha > 0;
        this.uwielbionaCecha = uwielbionaCecha - 1;
    }

    public int uwielbionaCecha() {
        return uwielbionaCecha;
    }

    @Override
    public List<Kandydat> rozważaniKandydaci() {
        return okręgWyborczy(true).kandydaci(true);
    }
}
