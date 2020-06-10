package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.osoba.kandydat.Kandydat;
import wybory.partia.Partia;
import wybory.pomoce.Pomoce;

import java.util.List;

public class ŻelaznyElektoratPartyjny extends WyborcaJednopartyjny {

    public ŻelaznyElektoratPartyjny(String imię, String nazwisko,
                                    OkręgWyborczy okręgWyborczy,
                                    Partia uwielbionaPartia) {
        super(imię, nazwisko, okręgWyborczy, uwielbionaPartia);
    }

    @Override
    public Kandydat wybranyKandydat() {
        List<Kandydat> kandydaci = rozważaniKandydaci();
        return Pomoce.wybierzLosowy(kandydaci);
    }
}
