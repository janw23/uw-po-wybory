package wybory.pomoce.wektor;

public class WektorOgraniczony extends Wektor {

    private final int ograniczenie;

    public WektorOgraniczony(int rozmiar, int ograniczenie) {
        super(rozmiar);
        this.ograniczenie = Math.abs(ograniczenie);
    }

    public WektorOgraniczony(Wektor w, int ograniczenie) {
        super(w);
        this.ograniczenie = ograniczenie;
        ograniczWspółrzędne();
    }

    public WektorOgraniczony(int[] współrzędne, int ograniczenie) {
        super(współrzędne);
        this.ograniczenie = ograniczenie;
        ograniczWspółrzędne();
    }

    private int ogranicz(int val) {
        return Math.max(Math.min(val, ograniczenie), -ograniczenie);
    }

    private void ograniczWspółrzędne() {
        for (int i = 0; i < liczbaWspółrzędnych(); i++)
            współrzędne[i] = ogranicz(współrzędne[i]);
    }

    @Override
    public void ustawWspółrzędną(int index, int wartość) {
        super.ustawWspółrzędną(index, ogranicz(wartość));
    }

    @Override
    public void dodaj(Wektor b) {
        super.dodaj(b);
        ograniczWspółrzędne();
    }

    //japierw dodaje, a potem ogranicza współrzędne
    public static WektorOgraniczony suma
            (Wektor a, Wektor b, int ograniczenie) {
        return new WektorOgraniczony(suma(a, b), ograniczenie);
    }
}
