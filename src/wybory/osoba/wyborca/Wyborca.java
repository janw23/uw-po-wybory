package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.osoba.Osoba;

public abstract class Wyborca extends Osoba {

    //numery typów odpowiadają treści zadania
    public static final int TYP_ŻELAZNY_ELEKTORAT_PARTYJNY           = 1;
    public static final int TYP_ŻELAZNY_ELEKTORAT_KANDYDATA          = 2;
    public static final int TYP_MINIMALIZUJĄCY_JEDNOCECHOWY          = 3;
    public static final int TYP_MAKSYMALIZUJĄCY_JEDNOCECHOWY         = 4;
    public static final int TYP_WSZECHSTRONNY                        = 5;
    public static final int TYP_MINIMALIZUJĄCY_JEDNOCECHOWOPARTYJNY  = 6;
    public static final int TYP_MAKSYMALIZUJĄCY_JEDNOCECHOWOPARTYJNY = 7;
    public static final int TYP_WSZECHSTRONNY_JEDNOCECHOWOPARTYJNY   = 8;

    private final OkręgWyborczy okręgWyborczy;
    private final int typ;

    public Wyborca(String imię, String nazwisko,
                   OkręgWyborczy okręgWyborczy, int typ) {

        super(imię, nazwisko);
        this.okręgWyborczy = okręgWyborczy;
        this.typ = typ;
    }

}
