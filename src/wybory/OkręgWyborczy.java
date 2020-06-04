package wybory;

import wybory.osoba.wyborca.Wyborca;

public class OkręgWyborczy {
    private final int numer;
    private final Wyborca[] wyborcy;
    private OkręgWyborczy scalonyZ = null;

    public OkręgWyborczy(int numer, int liczbaWyborców) {
        assert liczbaWyborców % 10 == 0; //liczba wyborców jest postaci 10k
        this.numer = numer;
        wyborcy = new Wyborca[liczbaWyborców];
    }

    //Zwraca numer okręgu z uwzględnieniem możliwości scalenia
    public int dajNumer() {
        if (scalonyZ == null)
            return numer;

        return Math.min(numer, scalonyZ.numer);
    }

    public static void scal(OkręgWyborczy okręgA, OkręgWyborczy okręgB) {
        //@todo Implementacja uwzględniająca modyfikację wyborców
    }

}
