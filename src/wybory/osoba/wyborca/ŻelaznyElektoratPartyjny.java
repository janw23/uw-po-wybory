package wybory.osoba.wyborca;

import wybory.strukturyWyborcze.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.strukturyWyborcze.Partia;

import java.util.List;

import static wybory.pomoce.Pomoce.wybierzLosowy;

public class ŻelaznyElektoratPartyjny extends WyborcaJednopartyjny {

    public ŻelaznyElektoratPartyjny(String imię, String nazwisko,
                                    OkręgWyborczy okręgWyborczy,
                                    Partia uwielbionaPartia) {
        super(imię, nazwisko, okręgWyborczy, uwielbionaPartia);
    }

    @Override
    public Kandydat wybranyKandydat() {
        List<Kandydat> kandydaci = rozważaniKandydaci();
        return wybierzLosowy(kandydaci);
    }
}
