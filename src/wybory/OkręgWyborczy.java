package wybory;

import wybory.osoba.kandydat.Kandydat;
import wybory.osoba.wyborca.Wyborca;
import wybory.partia.Partia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class OkręgWyborczy {

    private final int numer;
    private OkręgWyborczy scalonyZ = null;

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

    public List<Wyborca> wyborcy(boolean uwzględnijScalanie) {
        if (!uwzględnijScalanie || !scalony())
            return this.wyborcy;

        List<Wyborca> wszyscyWyborcy = new ArrayList<>(wyborcy);
        wszyscyWyborcy.addAll(scalonyZ.wyborcy);

        return wszyscyWyborcy;
    }

    public List<Kandydat> kandydaci(boolean uwzględnijScalanie) {
        if (!uwzględnijScalanie || !scalony())
            return this.kandydaci;

        List<Kandydat> wszyscyKandydaci = new ArrayList<>(kandydaci);
        wszyscyKandydaci.addAll(scalonyZ.kandydaci);

        return wszyscyKandydaci;
    }

    public List<Kandydat> kandydaciNależącyDoPartii(Partia partia,
                                                    boolean uwzględnijScalanie) {
        List<Kandydat> kandydaci = new LinkedList<>(kandydaci(uwzględnijScalanie));

        Predicate<Kandydat> zInnejPartii = (o) -> !o.należyDoPartii(partia);
        kandydaci.removeIf(zInnejPartii);

        return kandydaci;
    }

    public boolean jestGłówny() {
        return numerOkręgu(false) == numerOkręgu(true);
    }

    public OkręgWyborczy dajGłówny() {
        if (jestGłówny())
            return this;

        assert scalonyZ != null;
        return scalonyZ;
    }

    public static void scal(OkręgWyborczy okręgA, OkręgWyborczy okręgB) {
        okręgA.scalonyZ = okręgB;
        okręgB.scalonyZ = okręgA;
    }
}
