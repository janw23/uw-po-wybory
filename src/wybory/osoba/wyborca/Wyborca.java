package wybory.osoba.wyborca;

import wybory.OkręgWyborczy;
import wybory.osoba.Osoba;
import wybory.osoba.kandydat.Kandydat;

import java.util.List;
import java.util.Objects;

public abstract class Wyborca extends Osoba implements Cloneable {

    //numery typów odpowiadają treści zadania
    public static final int TYP_ŻELAZNY_ELEKTORAT_PARTYJNY = 1;
    public static final int TYP_ŻELAZNY_ELEKTORAT_KANDYDATA = 2;
    public static final int TYP_MINIMALIZUJĄCY_JEDNOCECHOWY = 3;
    public static final int TYP_MAKSYMALIZUJĄCY_JEDNOCECHOWY = 4;
    public static final int TYP_WSZECHSTRONNY = 5;
    public static final int TYP_MINIMALIZUJĄCY_JEDNOCECHOWOPARTYJNY = 6;
    public static final int TYP_MAKSYMALIZUJĄCY_JEDNOCECHOWOPARTYJNY = 7;
    public static final int TYP_WSZECHSTRONNY_JEDNOPARTYJNY = 8;

    public static boolean jednocechowy(int typ) {
        switch (typ) {
            case TYP_MINIMALIZUJĄCY_JEDNOCECHOWY:
            case TYP_MAKSYMALIZUJĄCY_JEDNOCECHOWY:
            case TYP_MINIMALIZUJĄCY_JEDNOCECHOWOPARTYJNY:
            case TYP_MAKSYMALIZUJĄCY_JEDNOCECHOWOPARTYJNY:
                return true;
            default:
                return false;
        }
    }

    public static boolean jednopartyjny(int typ) {
        switch (typ) {
            case TYP_ŻELAZNY_ELEKTORAT_PARTYJNY:
            case TYP_MINIMALIZUJĄCY_JEDNOCECHOWOPARTYJNY:
            case TYP_MAKSYMALIZUJĄCY_JEDNOCECHOWOPARTYJNY:
            case TYP_WSZECHSTRONNY_JEDNOPARTYJNY:
                return true;
            default:
                return false;
        }
    }

    public static boolean jednokandydatowy(int typ) {
        return typ == TYP_ŻELAZNY_ELEKTORAT_KANDYDATA;
    }

    public static boolean wszechstronny(int typ) {
        return typ == TYP_WSZECHSTRONNY ||
                typ == TYP_WSZECHSTRONNY_JEDNOPARTYJNY;
    }

    protected final OkręgWyborczy okręgWyborczy;

    public Wyborca(String imię, String nazwisko, OkręgWyborczy okręgWyborczy) {
        super(imię, nazwisko);
        Objects.requireNonNull(okręgWyborczy);
        this.okręgWyborczy = okręgWyborczy;
    }

    public abstract Kandydat wybranyKandydat();

    abstract List<Kandydat> rozważaniKandydaci();

    public OkręgWyborczy okręgWyborczy(boolean uwzględnijScalanie) {
        if (!uwzględnijScalanie)
            return okręgWyborczy;

        return okręgWyborczy.dajGłówny();
    }

    public abstract Object clone();
}
