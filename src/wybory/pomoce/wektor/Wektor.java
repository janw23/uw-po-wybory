package wybory.pomoce.wektor;

import java.util.Arrays;

public class Wektor {
    final int[] współrzędne;

    public Wektor(int rozmiar) {
        współrzędne = new int[rozmiar];
    }

    public Wektor(Wektor w) {
        współrzędne = new int[w.współrzędne.length];
        System.arraycopy(w.współrzędne, 0,
                współrzędne, 0, liczbaWspółrzędnych());
    }

    public Wektor(int[] współrzędne) {
        this.współrzędne = new int[współrzędne.length];
        System.arraycopy(współrzędne, 0,
                this.współrzędne, 0, liczbaWspółrzędnych());
    }

    public int liczbaWspółrzędnych() {
        return współrzędne.length;
    }

    public void ustawWspółrzędną(int index, int wartość) {
        współrzędne[index] = wartość;
    }

    public int dajWspółrzędną(int index) {
        return współrzędne[index];
    }

    public static int iloczynSkalarny(Wektor a, Wektor b) {
        assert a.liczbaWspółrzędnych() == b.liczbaWspółrzędnych();

        int suma = 0;

        for (int i = 0; i < a.liczbaWspółrzędnych(); i++)
            suma += a.współrzędne[i] * b.współrzędne[i];

        return suma;
    }

    public int iloczynSkalarny(Wektor b) {
        return iloczynSkalarny(this, b);
    }

    public void dodaj(Wektor b) {
        assert liczbaWspółrzędnych() == b.liczbaWspółrzędnych();

        for (int i = 0; i < liczbaWspółrzędnych(); i++)
            współrzędne[i] += b.współrzędne[i];
    }

    public static Wektor suma(Wektor a, Wektor b) {
        Wektor w = new Wektor(a);
        w.dodaj(b);
        return w;
    }

    public String toString() {
        return Arrays.toString(współrzędne);
    }
}
