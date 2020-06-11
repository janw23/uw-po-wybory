package wybory.osoba.wyborca;

import wybory.strukturyWyborcze.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.strukturyWyborcze.Partia;

import java.util.List;
import java.util.Objects;

public abstract class WyborcaJednocechowopartyjny
        extends Wyborca implements Jednocechowy, Jednopartyjny {

    protected final Partia uwielbionaPartia;
    protected final int uwielbionaCecha;

    public WyborcaJednocechowopartyjny(String imię, String nazwisko,
                                       OkręgWyborczy okręgWyborczy,
                                       Partia uwielbionaPartia,
                                       int uwielbionaCecha) {

        super(imię, nazwisko, okręgWyborczy);
        Objects.requireNonNull(uwielbionaPartia);
        this.uwielbionaPartia = uwielbionaPartia;
        this.uwielbionaCecha = uwielbionaCecha;
    }

    public Partia uwielbionaPartia() {
        return this.uwielbionaPartia;
    }

    public int uwielbionaCecha() {
        return uwielbionaCecha;
    }

    @Override
    List<Kandydat> rozważaniKandydaci() {
        return okręgWyborczy(true)
                .kandydaciNależącyDoPartii(uwielbionaPartia(), true);
    }
}
