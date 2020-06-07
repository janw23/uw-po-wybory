package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.partia.Partia;

import java.util.Objects;

public abstract class WyborcaJednopartyjny
        extends Wyborca implements Jednopartyjny {

    private final Partia uwielbionaPartia;

    public WyborcaJednopartyjny(String imię, String nazwisko,
                                OkręgWyborczy okręgWyborczy,
                                Partia uwielbionaPartia) {

        super(imię, nazwisko, okręgWyborczy);
        Objects.requireNonNull(uwielbionaPartia);
        this.uwielbionaPartia = uwielbionaPartia;
    }

}
