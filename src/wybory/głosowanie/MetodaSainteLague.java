package wybory.głosowanie;

public class MetodaSainteLague extends MetodaNajwiększychIlorazów {

    @Override
    public final String nazwa() {
        return "Metoda Sainte-Laguë";
    }

    @Override
    float obliczIlorazPrzyjętąMetodą(int liczbaGłosówNaPartię, int numerIlorazu) {
        return (float) liczbaGłosówNaPartię / (2 * numerIlorazu - 1);
    }
}
