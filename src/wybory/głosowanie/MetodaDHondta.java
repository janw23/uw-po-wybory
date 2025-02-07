package wybory.głosowanie;

public class MetodaDHondta extends MetodaNajwiększychIlorazów {

    @Override
    public final String nazwa() {
        return "Metoda D’Hondta";
    }

    @Override
    float obliczIlorazPrzyjętąMetodą(int liczbaGłosówNaPartię, int numerIlorazu) {
        return (float) liczbaGłosówNaPartię / numerIlorazu;
    }
}
