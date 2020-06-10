package wybory;

import wybory.osoba.kandydat.Kandydat;
import wybory.osoba.wyborca.Wyborca;
import wybory.partia.Partia;

import java.util.ArrayList;
import java.util.List;

public class OkręgWyborczy {
    private final int numer;
    private final OkręgWyborczy scalonyZ = null;

    private final int liczbaWyborców;
    private final List<Wyborca> wyborcy;
    private final List<Kandydat> kandydaci;

    public OkręgWyborczy(int numer, int liczbaWyborców) {
        assert liczbaWyborców % 10 == 0; //liczba wyborców jest postaci 10k
        this.numer = numer;
        this.liczbaWyborców = liczbaWyborców;

        wyborcy = new ArrayList<>(liczbaWyborców);
        kandydaci = new ArrayList<>();
    }

    public boolean scalony() {
        return scalonyZ != null;
    }

    public OkręgWyborczy scalonyZ() {
        return scalonyZ;
    }

    public int numerOkręgu(boolean uwzględnijScalanie) {
        if (!uwzględnijScalanie || !scalony())
            return numer;

        return Math.min(numer, scalonyZ.numer);
    }

    public int liczbaWyborców(boolean uwzględnijScalanie) {
        if (!uwzględnijScalanie || !scalony())
            return liczbaWyborców;

        return liczbaWyborców + scalonyZ.liczbaWyborców;
    }

    public int liczbaMandatów(boolean uwzględnijScalanie) {
        return liczbaWyborców(uwzględnijScalanie) / 10;
    }

    public void dodajWyborcę(Wyborca wyborca) {
        wyborcy.add(wyborca);
        assert wyborcy.size() <= liczbaWyborców;
    }

    public void dodajKandydata(Kandydat kandydat) {
        kandydaci.add(kandydat);
    }

    public Kandydat znajdźKandydata(Partia partia, int pozycjaNaLiście) {
        for (Kandydat kandydat : kandydaci) {
            if (kandydat.pozycjaNaLiście(false) == pozycjaNaLiście &&
                    kandydat.należyDoPartii(partia)) {
                return kandydat;
            }
        }
        return null;
    }

    public List<Wyborca> wyborcy() {
        return this.wyborcy;
    }

    public List<Kandydat> kandydaci() {
        return this.kandydaci;
    }

    public static void scal(OkręgWyborczy okręgA, OkręgWyborczy okręgB) {
        //@todo Implementacja uwzględniająca modyfikację wyborców
    }

    //@todo Wypisywanie na końcu może być po scalonych albo podstawowych

}
