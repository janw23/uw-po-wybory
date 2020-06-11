package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.pomoce.Pomoce;
import wybory.pomoce.Wartościowanie;

import java.util.List;
import java.util.Objects;

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

    @Override
    public Object clone() {
        return new MaksymalizującyJednocechowy(imię, nazwisko, okręgWyborczy, uwielbionaCecha);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaksymalizującyJednocechowy that = (MaksymalizującyJednocechowy) o;
        return uwielbionaCecha == that.uwielbionaCecha;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uwielbionaCecha);
    }

}
