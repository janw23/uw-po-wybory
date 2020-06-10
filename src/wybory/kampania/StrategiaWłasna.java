package wybory.kampania;

import wybory.OkręgWyborczy;
import wybory.partia.Partia;
import wybory.pomoce.para.Para;

public class StrategiaWłasna extends StrategiaKampanii{
    @Override
    public Para<DziałanieKampanijne, OkręgWyborczy> wybierzNajlepszeDziałanieKampanijne(DaneKampanii daneKampanii, Partia partia, int pozostałyBudżet) {
        return null;
    }
}
