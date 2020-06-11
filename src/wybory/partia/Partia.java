package wybory.partia;

import wybory.OkręgWyborczy;
import wybory.kampania.DaneKampanii;
import wybory.kampania.DziałanieKampanijne;
import wybory.kampania.StrategiaKampanii;
import wybory.osoba.kandydat.Kandydat;
import wybory.pomoce.para.Para;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class Partia {
    private final String nazwa;
    private int budżetKampanii;
    private final StrategiaKampanii strategiaKampanii;

    public Partia(String nazwa, int budżetKampanii, StrategiaKampanii strategiaKampanii) {

        this.nazwa = nazwa;
        this.budżetKampanii = budżetKampanii;
        this.strategiaKampanii = strategiaKampanii;
    }

    public Partia(Partia partia) {
        this.nazwa = partia.nazwa;
        this.budżetKampanii = partia.budżetKampanii;
        this.strategiaKampanii = partia.strategiaKampanii;
    }

    public String nazwa() {
        return nazwa;
    }

    public List<Kandydat> kandydaciWOkręgu(OkręgWyborczy okręg) {
        Predicate<Kandydat> nieZTejPartii = kandydat -> !kandydat.należyDoPartii(this);
        List<Kandydat> kandydaci = new LinkedList<>(okręg.kandydaci(true));
        kandydaci.removeIf(nieZTejPartii);
        return kandydaci;
    }

    //skraca zapis funkcji wyboru działania kampanijnego
    private Para<DziałanieKampanijne, OkręgWyborczy> wybierzDziałanie(DaneKampanii daneKampanii,
                                                                      int pozostałyBudżet) {
        return strategiaKampanii.wybierzNajlepszeDziałanieKampanijne(daneKampanii, this, pozostałyBudżet);
    }

    private void wykonajDziałanie(Para<DziałanieKampanijne, OkręgWyborczy> działanie) {
        OkręgWyborczy okręg = działanie.drugi();
        budżetKampanii -= działanie.pierwszy().obliczKosztWykonania(okręg);
        assert budżetKampanii >= 0;
        działanie.pierwszy().wykonajNa(okręg);
    }

    //zwraca false, jeśli nie może wykonać żadnego działania
    public boolean wykonajDziałanieWRamachKampanii(DaneKampanii daneKampanii) {
        Para<DziałanieKampanijne, OkręgWyborczy> wybraneDziałanie =
                wybierzDziałanie(daneKampanii, budżetKampanii);

        //zabrakło pieniędzy
        if (wybraneDziałanie == null)
            return false;

        wykonajDziałanie(wybraneDziałanie);
        return true;
    }
}