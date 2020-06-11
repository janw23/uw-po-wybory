package wybory.kampania;

import wybory.strukturyWyborcze.OkręgWyborczy;
import wybory.głosowanie.MetodaLiczeniaGłosów;

import java.util.List;

//ułatwia przechowywanie danych o kampanii
public class DaneKampanii {
    private final List<DziałanieKampanijne> działaniaKampanijne;
    private final List<OkręgWyborczy> okręgiWyborcze;
    private final MetodaLiczeniaGłosów metodaLiczeniaGłosów;

    public DaneKampanii(List<DziałanieKampanijne> działaniaKampanijne,
                        List<OkręgWyborczy> okręgiWyborcze,
                        MetodaLiczeniaGłosów metodaLiczeniaGłosów) {
        this.działaniaKampanijne = działaniaKampanijne;
        this.okręgiWyborcze = okręgiWyborcze;
        this.metodaLiczeniaGłosów = metodaLiczeniaGłosów;

        assert tylkoGłówneOkręgiWyborcze();
    }

    public MetodaLiczeniaGłosów metodaLiczeniaGłosów() {
        return metodaLiczeniaGłosów;
    }

    public List<OkręgWyborczy> okręgiWyborcze() {
        return okręgiWyborcze;
    }

    public List<DziałanieKampanijne> działaniaKampanijne() {
        return działaniaKampanijne;
    }

    private boolean tylkoGłówneOkręgiWyborcze() {
        for (OkręgWyborczy okręg : okręgiWyborcze)
            if (!okręg.jestGłówny())
                return false;

        return true;
    }
}
