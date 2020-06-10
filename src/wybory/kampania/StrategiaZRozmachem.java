package wybory.kampania;

import wybory.OkręgWyborczy;
import wybory.partia.Partia;
import wybory.pomoce.Pomoce;
import wybory.pomoce.Wartościowanie;
import wybory.pomoce.para.Para;

import java.util.List;

public class StrategiaZRozmachem extends StrategiaKampanii {

    @Override
    public Para<DziałanieKampanijne, OkręgWyborczy> wybierzNajlepszeDziałanieKampanijne
            (DaneKampanii daneKampanii, Partia partia, int pozostałyBudżet) {

        List<Para<DziałanieKampanijne, OkręgWyborczy>> możliwości =
                możliweDziałania(daneKampanii, pozostałyBudżet);

        Wartościowanie<Para<DziałanieKampanijne, OkręgWyborczy>> wartościowanie = new Wartościowanie<>() {
            @Override
            public int wartość(Para<DziałanieKampanijne, OkręgWyborczy> o) {
                return kosztWykonania(o);
            }
        };

        return Pomoce.wybierzNajlepszyLosowy(możliwości, wartościowanie);
    }

}
