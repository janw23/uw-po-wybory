package wybory.osoba.kandydat;

import wybory.OkręgWyborczy;
import wybory.osoba.Osoba;
import wybory.partia.Partia;
import wybory.pomoce.wektor.WektorOgraniczony;

public class Kandydat extends Osoba {
    private final Partia partia;
    private final OkręgWyborczy okręgWyborczy;
    private int pozycjaNaLiście;
    private WektorOgraniczony cechy; //@todo final?

    public Kandydat(String imię, String nazwisko,
                    OkręgWyborczy okręgWyborczy,
                    Partia partia, int pozycjaNaLiście) {

        super(imię, nazwisko);
        this.okręgWyborczy = okręgWyborczy;
        this.partia = partia;
        this.pozycjaNaLiście = pozycjaNaLiście;
    }
}
