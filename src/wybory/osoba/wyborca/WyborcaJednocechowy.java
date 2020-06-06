package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;

public abstract class WyborcaJednocechowy
        extends Wyborca implements Jednocechowy {

    private final int uwielbionaCecha;  //numer uwielbionej cechy

    public WyborcaJednocechowy(String imię, String nazwisko,
                               OkręgWyborczy okręgWyborczy,
                               int uwielbionaCecha) {

        super(imię, nazwisko, okręgWyborczy);
        assert uwielbionaCecha >= 0;
        this.uwielbionaCecha = uwielbionaCecha;
    }

}
