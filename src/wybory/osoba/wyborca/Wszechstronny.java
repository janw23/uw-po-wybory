package wybory.osoba.wyborca;

import wybory.strukturyWyborcze.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.pomoce.Wartościowanie;
import wybory.pomoce.wektor.Wektor;
import wybory.pomoce.wektor.WektorOgraniczony;

import java.util.List;

import static wybory.pomoce.Pomoce.wybierzNajlepszyLosowy;

public class Wszechstronny extends Wyborca {

    protected WektorOgraniczony wagiCech;

    public Wszechstronny(String imię, String nazwisko,
                         OkręgWyborczy okręgWyborczy,
                         WektorOgraniczony wagiCech) {

        super(imię, nazwisko, okręgWyborczy);
        this.wagiCech = wagiCech;
    }

    public WektorOgraniczony wagiCech() {
        return this.wagiCech;
    }

    public void wagiCech(WektorOgraniczony wagiCech) {
        this.wagiCech = wagiCech;
    }

    public void zmieńWagiCechOWektor(Wektor wektor) {
        assert wektor.liczbaWspółrzędnych() == wagiCech.liczbaWspółrzędnych();
        wagiCech.dodaj(wektor);
    }

    public int sumaWażonaCech(Kandydat kandydat) {
        return wagiCech.iloczynSkalarny(kandydat.cechy());
    }

    @Override
    public Kandydat wybranyKandydat() {
        List<Kandydat> kandydaci = rozważaniKandydaci();

        Wartościowanie<Kandydat> najwyższaSumaWażona = new Wartościowanie<Kandydat>() {
            @Override
            public int wartość(Kandydat o) {
                return sumaWażonaCech(o);
            }
        };

        return wybierzNajlepszyLosowy(kandydaci, najwyższaSumaWażona);
    }

    @Override
    List<Kandydat> rozważaniKandydaci() {
        return okręgWyborczy(true).kandydaci(true);
    }
}
