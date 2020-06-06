package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.partia.Partia;

public class MaksymalizującyJednocechowopartyjny
        extends WyborcaJednocechowopartyjny {
    
    public MaksymalizującyJednocechowopartyjny
            (String imię, String nazwisko, OkręgWyborczy okręgWyborczy,
             Partia uwielbionaPartia, int uwielbionaCecha) {

        super(imię, nazwisko, okręgWyborczy,
                uwielbionaPartia, uwielbionaCecha);
    }
}
