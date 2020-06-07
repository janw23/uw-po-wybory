package wybory.partia;

import wybory.OkręgWyborczy;
import wybory.kampania.DaneKampanii;
import wybory.kampania.DziałanieKampanijne;
import wybory.kampania.StrategiaKampanii;
import wybory.pomoce.para.Para;

import java.util.Objects;

public class Partia {
    private final String nazwa;
    private int budżetKampanii;
    private final StrategiaKampanii strategiaKampanii;

    public Partia(String nazwa, int budżetKampanii,
                  StrategiaKampanii strategiaKampanii) {

        this.nazwa = nazwa;
        this.budżetKampanii = budżetKampanii;
        this.strategiaKampanii = strategiaKampanii;
    }

    public String nazwa() {
        return nazwa;
    }

    //skraca zapis funkcji wyboru działania kampanijnego
    private Para<DziałanieKampanijne, OkręgWyborczy> wybierzDziałanie
            (DaneKampanii daneKampanii, int pozostałyBudżet) {

        return strategiaKampanii.wybierzNajlepszeDziałanieKampanijne
                (daneKampanii, pozostałyBudżet);
    }

    public void przeprowadźKampanię(DaneKampanii daneKampanii) {
        //@todo Ze scalonej pary powinny być tylko brane pierwsze okręgi

        Para<DziałanieKampanijne, OkręgWyborczy> wybraneDziałanie;
        while ((wybraneDziałanie = wybierzDziałanie(daneKampanii, budżetKampanii)) != null) {
            //@todo Tutaj skończyłem.
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partia partia = (Partia) o;
        return budżetKampanii == partia.budżetKampanii &&
                Objects.equals(nazwa, partia.nazwa) &&
                Objects.equals(strategiaKampanii, partia.strategiaKampanii);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nazwa, budżetKampanii, strategiaKampanii);
    }
}