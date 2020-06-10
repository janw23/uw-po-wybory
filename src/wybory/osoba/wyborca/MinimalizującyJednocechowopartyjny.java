package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.partia.Partia;
import wybory.pomoce.Pomoce;
import wybory.pomoce.Wartościowanie;

import java.util.List;

public class MinimalizującyJednocechowopartyjny extends WyborcaJednocechowopartyjny {

    public MinimalizującyJednocechowopartyjny
            (String imię, String nazwisko, OkręgWyborczy okręgWyborczy,
             Partia uwielbionaPartia, int uwielbionaCecha) {

        super(imię, nazwisko, okręgWyborczy,
                uwielbionaPartia, uwielbionaCecha);
    }

    @Override
    public Kandydat wybranyKandydat() {
        List<Kandydat> kandydaci = okręgWyborczy(true).
                kandydaciNależącyDoPartii(uwielbionaPartia(), true);

        Wartościowanie<Kandydat> najniższaCecha = new Wartościowanie<Kandydat>() {
            @Override
            public int wartość(Kandydat o) {
                return -o.cechy().dajWspółrzędną(uwielbionaCecha());
            }
        };

        return Pomoce.wybierzNajlepszyLosowy(kandydaci, najniższaCecha);
    }
}
