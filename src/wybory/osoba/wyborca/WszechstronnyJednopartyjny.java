package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.partia.Partia;
import wybory.pomoce.Wartościowanie;
import wybory.pomoce.wektor.WektorOgraniczony;

import java.util.List;

import static wybory.pomoce.Pomoce.wybierzNajlepszyLosowy;

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

    @Override
    public Object clone() {
        WektorOgraniczony noweWagi = new WektorOgraniczony(this.wagiCech);
        return new WszechstronnyJednopartyjny(imię, nazwisko, okręgWyborczy, uwielbionaPartia, noweWagi);
    }
}
