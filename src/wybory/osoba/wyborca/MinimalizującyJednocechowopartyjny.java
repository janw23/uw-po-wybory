package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.partia.Partia;

public class MinimalizującyJednocechowopartyjny
        extends WyborcaJednocechowopartyjny {

    public MinimalizującyJednocechowopartyjny
            (String imię, String nazwisko, OkręgWyborczy okręgWyborczy,
             Partia uwielbionaPartia, int uwielbionaCecha) {

        super(imię, nazwisko, okręgWyborczy,
                uwielbionaPartia, uwielbionaCecha);
    }
}
