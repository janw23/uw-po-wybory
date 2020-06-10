package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.partia.Partia;

import java.util.List;
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
        assert uwielbionaCecha > 0;
        this.uwielbionaCecha = uwielbionaCecha - 1;
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
