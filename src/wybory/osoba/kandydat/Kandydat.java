package wybory.osoba.kandydat;

import wybory.OkręgWyborczy;
import wybory.osoba.Osoba;
import wybory.partia.Partia;
import wybory.pomoce.wektor.Wektor;
import wybory.pomoce.wektor.WektorOgraniczony;

public class Kandydat extends Osoba {
    private final Partia partia;
    private final OkręgWyborczy okręgWyborczy;
    private final int pozycjaNaLiście;
    private WektorOgraniczony cechy;

    public Kandydat(String imię, String nazwisko,
                    OkręgWyborczy okręgWyborczy,
                    Partia partia, int pozycjaNaLiście,
                    WektorOgraniczony cechy) {

        super(imię, nazwisko);
        this.okręgWyborczy = okręgWyborczy;
        this.partia = partia;
        this.pozycjaNaLiście = pozycjaNaLiście;
        this.cechy = cechy;
    }

    public int pozycjaNaLiście(boolean uwzględniajScalanie) {
        if (!uwzględniajScalanie || okręgWyborczy == null || !okręgWyborczy.scalony())
            return pozycjaNaLiście;

        return okręgWyborczy.scalonyZ().liczbaMandatów(false) + pozycjaNaLiście;
    }

    public boolean należyDoPartii(Partia partia) {
        return partia.equals(this.partia);
    }

    public Wektor cechy() {
        return cechy;
    }
}
