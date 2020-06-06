package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.partia.Partia;

import static wybory.pomoce.Pomoce.assessNotNull;

public abstract class WyborcaJednocechowopartyjny
        extends Wyborca implements Jednocechowy, Jednopartyjny {

    private final Partia uwielbionaPartia;
    private final int uwielbionaCecha;

    public WyborcaJednocechowopartyjny(String imię, String nazwisko,
                                       OkręgWyborczy okręgWyborczy,
                                       Partia uwielbionaPartia,
                                       int uwielbionaCecha) {

        super(imię, nazwisko, okręgWyborczy);
        assessNotNull(uwielbionaPartia);
        this.uwielbionaPartia = uwielbionaPartia;
        this.uwielbionaCecha = uwielbionaCecha;
    }
}
