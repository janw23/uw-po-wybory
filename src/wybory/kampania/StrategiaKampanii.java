package wybory.kampania;

import wybory.strukturyWyborcze.OkręgWyborczy;
import wybory.osoba.wyborca.Wszechstronny;
import wybory.osoba.wyborca.Wyborca;
import wybory.strukturyWyborcze.Partia;
import wybory.pomoce.Wartościowanie;
import wybory.pomoce.para.Para;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static wybory.pomoce.Pomoce.iloczynKartezjański;
import static wybory.pomoce.Pomoce.wybierzNajlepszyLosowy;

public abstract class StrategiaKampanii {

    //wykonuje najlepsze działanie kampanijne według danej strategii
    public abstract Para<DziałanieKampanijne, OkręgWyborczy>
    wybierzNajlepszeDziałanieKampanijne(DaneKampanii daneKampanii, Partia partia, int pozostałyBudżet);

    //liczy koszt wykonania danej pary <działanie, okręg>
    int kosztWykonania(Para<DziałanieKampanijne, OkręgWyborczy> możliwość) {
        return możliwość.pierwszy().obliczKosztWykonania(możliwość.drugi());
    }

    //zwraca wszystkie pary <działanie, okręg>, których wykonanie mieści się w budżecie
    List<Para<DziałanieKampanijne, OkręgWyborczy>> możliweDziałania(DaneKampanii dane, int budżet) {
        Predicate<Para<DziałanieKampanijne, OkręgWyborczy>> zaDrogi =
                możliwość -> kosztWykonania(możliwość) > budżet;

        List<Para<DziałanieKampanijne, OkręgWyborczy>> możliwości =
                iloczynKartezjański(dane.działaniaKampanijne(), dane.okręgiWyborcze());

        możliwości.removeIf(zaDrogi);
        return możliwości;
    }

    List<Wszechstronny> wyborcyWszechstronniSpełniającyPredykat(OkręgWyborczy okręg,
                                                                Predicate<Wszechstronny> predykat) {
        List<Wszechstronny> wyborcy = new ArrayList<>();

        for (Wyborca wyborca : okręg.wyborcy(true))
            if (wyborca instanceof Wszechstronny && predykat.test((Wszechstronny) wyborca))
                wyborcy.add((Wszechstronny) wyborca);

        return wyborcy;
    }

    static Para<DziałanieKampanijne, OkręgWyborczy> możliwośćONajwiększymWartościowaniu
            (List<Para<Integer, Para<DziałanieKampanijne, OkręgWyborczy>>> możliwości) {

        Wartościowanie<Para<Integer, Para<DziałanieKampanijne, OkręgWyborczy>>> wartościowanie =
                new Wartościowanie<>() {
                    @Override
                    public int wartość(Para<Integer, Para<DziałanieKampanijne, OkręgWyborczy>> o) {
                        return o.pierwszy();
                    }
                };

        var najlepszy = wybierzNajlepszyLosowy(możliwości, wartościowanie);

        return najlepszy == null ? null : najlepszy.drugi();
    }
}
