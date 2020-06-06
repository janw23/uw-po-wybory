package wybory.partia;

import wybory.kampania.StrategiaKampanii;

import java.util.Objects;

public class Partia {
    private final String nazwa;
    private final int budżetKampanii;
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
