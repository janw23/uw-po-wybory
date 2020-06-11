package wybory.kampania;

import wybory.strukturyWyborcze.OkręgWyborczy;
import wybory.strukturyWyborcze.Partia;
import wybory.pomoce.Wartościowanie;
import wybory.pomoce.para.Para;

import static wybory.pomoce.Pomoce.wybierzNajlepszyLosowy;

public class StrategiaSkromna extends StrategiaKampanii {

    @Override
    public Para<DziałanieKampanijne, OkręgWyborczy> wybierzNajlepszeDziałanieKampanijne
            (DaneKampanii daneKampanii, Partia partia, int pozostałyBudżet) {

        var możliwości = możliweDziałania(daneKampanii, pozostałyBudżet);

        Wartościowanie<Para<DziałanieKampanijne, OkręgWyborczy>> wartościowanie = new Wartościowanie<>() {
            @Override
            public int wartość(Para<DziałanieKampanijne, OkręgWyborczy> o) {
                return Integer.MAX_VALUE - kosztWykonania(o);
            }
        };

        return wybierzNajlepszyLosowy(możliwości, wartościowanie);
    }
}
