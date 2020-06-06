package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.partia.Partia;

public class ŻelaznyElektoratPartyjny extends WyborcaJednopartyjny {

    public ŻelaznyElektoratPartyjny(String imię, String nazwisko,
                                    OkręgWyborczy okręgWyborczy,
                                    Partia uwielbionaPartia) {
        super(imię, nazwisko, okręgWyborczy, uwielbionaPartia);
    }
}
