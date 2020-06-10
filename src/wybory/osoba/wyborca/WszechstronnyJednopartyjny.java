package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.partia.Partia;
import wybory.pomoce.Pomoce;
import wybory.pomoce.Wartościowanie;
import wybory.pomoce.wektor.WektorOgraniczony;

import java.util.List;

public class WszechstronnyJednopartyjny extends Wszechstronny implements Jednopartyjny {

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

    //@todo Czy to jest potrzebne, czy definicja z nadklasy będzie w tym przypadku korzystać z rozważaniKandydaci w tej klasie?
    @Override
    public Kandydat wybranyKandydat() {
        List<Kandydat> kandydaci = rozważaniKandydaci();

        Wartościowanie<Kandydat> najwyższaSumaWażona = new Wartościowanie<Kandydat>() {
            @Override
            public int wartość(Kandydat o) {
                return sumaWażonaCech(o);
            }
        };

        return Pomoce.wybierzNajlepszyLosowy(kandydaci, najwyższaSumaWażona);
    }

    @Override
    List<Kandydat> rozważaniKandydaci() {
        return okręgWyborczy(true)
                .kandydaciNależącyDoPartii(uwielbionaPartia(), true);
    }
}
