package wybory.osoba.wyborca;

import wybory.strukturyWyborcze.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.pomoce.Wartościowanie;

import java.util.List;

import static wybory.pomoce.Pomoce.wybierzNajlepszyLosowy;

public class MaksymalizującyJednocechowy extends WyborcaJednocechowy {

    public MaksymalizującyJednocechowy(String imię, String nazwisko,
                                       OkręgWyborczy okręgWyborczy,
                                       int uwielbionaCecha) {

        super(imię, nazwisko, okręgWyborczy, uwielbionaCecha);
    }

    @Override
    public Kandydat wybranyKandydat() {
        List<Kandydat> kandydaci =
                okręgWyborczy(true).kandydaci(true);

        Wartościowanie<Kandydat> najwyższaCecha = new Wartościowanie<Kandydat>() {
            @Override
            public int wartość(Kandydat o) {
                return o.cechy().dajWspółrzędną(uwielbionaCecha());
            }
        };

        return wybierzNajlepszyLosowy(kandydaci, najwyższaCecha);
    }
}
