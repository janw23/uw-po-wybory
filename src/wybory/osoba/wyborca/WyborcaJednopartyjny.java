package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.partia.Partia;

import static wybory.pomoce.Pomoce.assessNotNull;

public abstract class WyborcaJednopartyjny
        extends Wyborca implements Jednopartyjny {

    private final Partia uwielbionaPartia;

    public WyborcaJednopartyjny(String imię, String nazwisko,
                                OkręgWyborczy okręgWyborczy,
                                Partia uwielbionaPartia) {

        super(imię, nazwisko, okręgWyborczy);
        assessNotNull(uwielbionaPartia);
        this.uwielbionaPartia = uwielbionaPartia;
    }

}
