package wybory.kampania;

import wybory.OkręgWyborczy;
import wybory.partia.Partia;
import wybory.pomoce.para.Para;

import java.util.List;
import java.util.function.Predicate;

import static wybory.pomoce.Pomoce.iloczynKartezjański;

public abstract class StrategiaKampanii {

    public static final int STRATEGIA_Z_ROZMACHEM = 1;
    public static final int STRATEGIA_SKROMNA = 2;
    public static final int STRATEGIA_ZACHŁANNA = 3;
    public static final int STRATEGIA_WŁASNA = 4; //@todo Zmienić nazwę?

    //wykonuje najlepsze działanie kampanijne według danej strategii
    public abstract Para<DziałanieKampanijne, OkręgWyborczy>
    wybierzNajlepszeDziałanieKampanijne(DaneKampanii daneKampanii, Partia partia, int pozostałyBudżet);

    public final Para<DziałanieKampanijne, OkręgWyborczy>
    wybierzNajlepszeDziałanieKampanijne(DaneKampanii daneKampanii, int pozostałyBudżet) {
        return wybierzNajlepszeDziałanieKampanijne(daneKampanii, null, pozostałyBudżet);
    }

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

}
