package wybory.osoba.wyborca;

import wybory.strukturyWyborcze.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.strukturyWyborcze.Partia;
import wybory.pomoce.wektor.WektorOgraniczony;

import java.util.List;

public class WszechstronnyJednopartyjny extends Wszechstronny implements Jednopartyjny {

    private Partia uwielbionaPartia;

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

    @Override
    List<Kandydat> rozważaniKandydaci() {
        return okręgWyborczy(true)
                .kandydaciNależącyDoPartii(uwielbionaPartia(), true);
    }
}
