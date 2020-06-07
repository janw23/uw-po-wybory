package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.partia.Partia;

import java.util.Objects;

public abstract class WyborcaJednocechowopartyjny
        extends Wyborca implements Jednocechowy, Jednopartyjny {

    private final Partia uwielbionaPartia;
    private final int uwielbionaCecha;

    public WyborcaJednocechowopartyjny(String imię, String nazwisko,
                                       OkręgWyborczy okręgWyborczy,
                                       Partia uwielbionaPartia,
                                       int uwielbionaCecha) {

        super(imię, nazwisko, okręgWyborczy);
        Objects.requireNonNull(uwielbionaPartia);
        this.uwielbionaPartia = uwielbionaPartia;
        this.uwielbionaCecha = uwielbionaCecha;
    }
}
