package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.partia.Partia;
import wybory.pomoce.wektor.Wektor;
import wybory.pomoce.wektor.WektorOgraniczony;

public class WszechstronnyJednopartyjny extends WyborcaJednopartyjny {

    private final Wektor wagiCech;

    public WszechstronnyJednopartyjny(String imię, String nazwisko,
                                      OkręgWyborczy okręgWyborczy,
                                      Partia uwielbionaPartia,
                                      WektorOgraniczony wagiCech) {

        super(imię, nazwisko, okręgWyborczy, uwielbionaPartia);
        this.wagiCech = wagiCech;
    }
}
