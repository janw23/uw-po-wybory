package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.partia.Partia;
import wybory.pomoce.Pomoce;
import wybory.pomoce.Wartościowanie;

import java.util.List;
import java.util.Objects;

import static wybory.pomoce.Pomoce.wybierzNajlepszyLosowy;

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

        return wybierzNajlepszyLosowy(kandydaci, najniższaCecha);
    }

    @Override
    public Object clone() {
        return new MinimalizującyJednocechowopartyjny(imię, nazwisko, okręgWyborczy, uwielbionaPartia, uwielbionaCecha);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinimalizującyJednocechowopartyjny that = (MinimalizującyJednocechowopartyjny) o;
        return uwielbionaCecha == that.uwielbionaCecha &&
                uwielbionaPartia.equals(that.uwielbionaPartia);
        //@todo equals dla partii
    }

    @Override
    public int hashCode() {
        return Objects.hash(uwielbionaCecha, uwielbionaPartia);
    }

}
