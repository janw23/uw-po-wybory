package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.partia.Partia;

import java.util.Objects;

//@todo Pozbyć się tego i zmienić wszechstronny na klasę z której się dzieciczy a to na interfejs
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

    @Override
    public Partia uwielbionaPartia() {
        return uwielbionaPartia;
    }
}
