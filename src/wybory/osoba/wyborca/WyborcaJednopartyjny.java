package wybory.osoba.wyborca;

import wybory.strukturyWyborcze.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.strukturyWyborcze.Partia;

import java.util.List;
import java.util.Objects;

public abstract class WyborcaJednopartyjny extends Wyborca implements Jednopartyjny {

    protected final Partia uwielbionaPartia;

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

    @Override
    List<Kandydat> rozważaniKandydaci() {
        return okręgWyborczy(true)
                .kandydaciNależącyDoPartii(uwielbionaPartia(), true);
    }
}
