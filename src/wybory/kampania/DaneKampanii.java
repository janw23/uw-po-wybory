package wybory.kampania;

import wybory.OkręgWyborczy;
import wybory.głosowanie.MetodaGłosowania;

import java.util.List;

//ułatwia przechowywanie danych o kampanii
public class DaneKampanii {
    private final List<DziałanieKampanijne> działaniaKampanijne;
    private final List<OkręgWyborczy> okręgiWyborcze;
    private final MetodaGłosowania metodaGłosowania;

    public DaneKampanii(List<DziałanieKampanijne> działaniaKampanijne,
                        List<OkręgWyborczy> okręgiWyborcze,
                        MetodaGłosowania metodaGłosowania) {
        this.działaniaKampanijne = działaniaKampanijne;
        this.okręgiWyborcze = null;
        this.metodaGłosowania = metodaGłosowania;
    }
}
