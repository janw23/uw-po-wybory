package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.partia.Partia;
import wybory.pomoce.wektor.WektorOgraniczony;

public class WszechstronnyJednopartyjny
        extends Wszechstronny implements Jednopartyjny {

    private final Partia uwielbionaPartia;

    public WszechstronnyJednopartyjny(String imię, String nazwisko,
                                      OkręgWyborczy okręgWyborczy,
                                      Partia uwielbionaPartia,
                                      WektorOgraniczony wagiCech) {

        super(imię, nazwisko, okręgWyborczy, wagiCech);
        this.uwielbionaPartia = uwielbionaPartia;
    }

    @Override
    public Partia uwielbionaPartia() {
        return this.uwielbionaPartia;
    }
}
