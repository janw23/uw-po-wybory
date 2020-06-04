package wybory.partia;

import wybory.kampania.StrategiaKampanii;

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
}
