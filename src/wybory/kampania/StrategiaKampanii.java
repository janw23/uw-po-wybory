package wybory.kampania;

import wybory.OkręgWyborczy;
import wybory.pomoce.para.Para;

public abstract class StrategiaKampanii {

    public static final int STRATEGIA_Z_ROZMACHEM = 1;
    public static final int STRATEGIA_SKROMNA = 2;
    public static final int STRATEGIA_ZACHŁANNA = 3;
    public static final int STRATEGIA_WŁASNA = 4; //@todo Zmienić nazwę?

    //wykonuje najlepsze działanie kampanijne według danej strategii
    public abstract Para<DziałanieKampanijne, OkręgWyborczy>
    wybierzNajlepszeDziałanieKampanijne(DaneKampanii daneKampanii,
                                        int pozostałyBudżet);

}
