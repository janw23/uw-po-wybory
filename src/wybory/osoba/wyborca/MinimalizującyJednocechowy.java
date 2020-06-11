package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.pomoce.Pomoce;
import wybory.pomoce.Wartościowanie;

import java.util.List;
import java.util.Objects;

import static wybory.pomoce.Pomoce.wybierzNajlepszyLosowy;

public class MinimalizującyJednocechowy extends WyborcaJednocechowy {

    public MinimalizującyJednocechowy(String imię, String nazwisko,
                                      OkręgWyborczy okręgWyborczy,
                                      int uwielbionaCecha) {
        super(imię, nazwisko, okręgWyborczy, uwielbionaCecha);
    }

    @Override
    public Kandydat wybranyKandydat() {
        List<Kandydat> kandydaci =
                okręgWyborczy(true).kandydaci(true);

        Wartościowanie<Kandydat> najniższaCecha = new Wartościowanie<Kandydat>() {
            @Override
            public int wartość(Kandydat o) {
                return -o.cechy().dajWspółrzędną(uwielbionaCecha());
            }
        };

        return wybierzNajlepszyLosowy(kandydaci, najniższaCecha);
    }

    @Override
    public Object clone() {
        return new MinimalizującyJednocechowy(imię, nazwisko, okręgWyborczy, uwielbionaCecha);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinimalizującyJednocechowy that = (MinimalizującyJednocechowy) o;
        return uwielbionaCecha == that.uwielbionaCecha;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uwielbionaCecha);
    }

}
