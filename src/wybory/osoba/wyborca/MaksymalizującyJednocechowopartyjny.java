package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.partia.Partia;
import wybory.pomoce.Pomoce;
import wybory.pomoce.Wartościowanie;

import java.util.List;
import java.util.Objects;

import static wybory.pomoce.Pomoce.wybierzNajlepszyLosowy;

public class MaksymalizującyJednocechowopartyjny extends WyborcaJednocechowopartyjny {

    public MaksymalizującyJednocechowopartyjny
            (String imię, String nazwisko, OkręgWyborczy okręgWyborczy,
             Partia uwielbionaPartia, int uwielbionaCecha) {

        super(imię, nazwisko, okręgWyborczy,
                uwielbionaPartia, uwielbionaCecha);
    }

    @Override
    public Kandydat wybranyKandydat() {
        List<Kandydat> kandydaci = okręgWyborczy(true)
                .kandydaciNależącyDoPartii(uwielbionaPartia(), true);

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
        return new MaksymalizującyJednocechowopartyjny(imię, nazwisko, okręgWyborczy, uwielbionaPartia, uwielbionaCecha);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WyborcaJednocechowopartyjny that = (WyborcaJednocechowopartyjny) o;
        return uwielbionaCecha == that.uwielbionaCecha &&
                uwielbionaPartia.equals(that.uwielbionaPartia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uwielbionaCecha, uwielbionaPartia);
    }
}
