package wybory;

import wybory.głosowanie.Głosowanie;
import wybory.głosowanie.MetodaLiczeniaGłosów;
import wybory.kampania.*;
import wybory.osoba.kandydat.Kandydat;
import wybory.osoba.wyborca.*;
import wybory.partia.Partia;
import wybory.pomoce.para.Para;
import wybory.pomoce.wektor.WektorOgraniczony;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static wybory.osoba.wyborca.Wyborca.*;

class FileReadingException extends Exception {
    public FileReadingException(Exception cause) {
        super(cause);
    }
}

class UnknownCampaignStrategyException extends Exception {
    private char znakStrategii;

    public UnknownCampaignStrategyException(char znakStrategii) {
        this.znakStrategii = znakStrategii;
    }

    @Override
    public String getMessage() {
        return "Znak strategii: " + znakStrategii;
    }
}

class UnknownPoliticalPartyException extends Exception {
    private String nazwaPartii;

    public UnknownPoliticalPartyException(String nazwaPartii) {
        this.nazwaPartii = nazwaPartii;
    }

    @Override
    public String getMessage() {
        return "Nazwa partii: " + nazwaPartii;
    }
}

class UnknownPoliticalVoterException extends Exception {
    private int typ;

    public UnknownPoliticalVoterException(int typ) {
        this.typ = typ;
    }

    @Override
    public String getMessage() {
        return "Kod wyborcy: " + typ;
    }
}

class NoSuchPoliticalCandidateException extends Exception {
    private final Partia partia;
    private final int pozycjaNaLiście;

    public NoSuchPoliticalCandidateException(Partia partia, int pozycjaNaLiście) {
        this.partia = partia;
        this.pozycjaNaLiście = pozycjaNaLiście;
    }

    @Override
    public String getMessage() {
        return "Partia:" + partia.toString() +
                ", pozycja na liście: " + pozycjaNaLiście;
    }
}

//główna klasa służąca do wczytania danych i
//symulacji procesu wyborczego
public class Wybory {

    private OkręgWyborczy[] okręgiWyborcze;
    private Partia[] partie;
    private DziałanieKampanijne[] działaniaKampanijne;
    private int liczbaCechKandydatów;
    private List<Para<Integer, Integer>> scalenia;

    public Wybory(File plik)
            throws FileReadingException, FileNotFoundException {

        super();
        WczytywaczDanych.wczytajWyboryZPliku(this, plik);
        scalOkręgi();
    }

    //konstruktor tworzący kopię
    public Wybory(Wybory wybory) {
        //głębokie kopie, ponieważ te obiekty mogą ulegać zmianie
        //@todo Upewnić się, że to rzeczywiście jest głęboka kopia
        okręgiWyborcze = wybory.okręgiWyborcze.clone();
        partie = wybory.partie.clone();

        //pozostałe cały czas w zasadzie są takie same jak na początku
        działaniaKampanijne = Arrays.copyOf(wybory.działaniaKampanijne, wybory.działaniaKampanijne.length);
        liczbaCechKandydatów = wybory.liczbaCechKandydatów;
        scalenia = List.copyOf(wybory.scalenia);

        assert wybory.equals(this);
    }

    private void scalOkręgi() {
        for (Para<Integer, Integer> scalenie : scalenia) {
            assert scalenie.drugi() == scalenie.pierwszy() + 1;
            OkręgWyborczy okręgA = okręgiWyborcze[scalenie.pierwszy() - 1];
            OkręgWyborczy okręgB = okręgiWyborcze[scalenie.drugi() - 1];
            assert okręgA.numerOkręgu(false) == scalenie.pierwszy();
            assert okręgB.numerOkręgu(false) == scalenie.drugi();
            OkręgWyborczy.scal(okręgA, okręgB);
        }
    }

    public void symulujWybory(List<MetodaLiczeniaGłosów> metodyGłosowania) {
        //@todo Użyć Stringbuildera i zwrócić String?
        for (MetodaLiczeniaGłosów metodaLiczeniaGłosów : metodyGłosowania) {
            Wybory wybory = new Wybory(this);
            SymulacjaWyborów.przeprowadźWybory(wybory, metodaLiczeniaGłosów);
            //@todo wybory.wypiszWynik() czy jakoś tak
        }
    }

    //okręgi główne to pojedyńcze okręgi lub te ze scalonych par, które mają mniejszy numer
    private List<OkręgWyborczy> okręgiGłówne() {
        List<OkręgWyborczy> okręgi = new LinkedList<>(asList(okręgiWyborcze));
        Predicate<OkręgWyborczy> jestPodrzędny = okręg -> !okręg.jestGłówny();

        okręgi.removeIf(jestPodrzędny);
        return okręgi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wybory wybory = (Wybory) o;
        return liczbaCechKandydatów == wybory.liczbaCechKandydatów &&
                Arrays.equals(okręgiWyborcze, wybory.okręgiWyborcze) &&
                Arrays.equals(partie, wybory.partie) &&
                Arrays.equals(działaniaKampanijne, wybory.działaniaKampanijne) &&
                Objects.equals(scalenia, wybory.scalenia);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(liczbaCechKandydatów, scalenia);
        result = 31 * result + Arrays.hashCode(okręgiWyborcze);
        result = 31 * result + Arrays.hashCode(partie);
        result = 31 * result + Arrays.hashCode(działaniaKampanijne);
        return result;
    }

    static class SymulacjaWyborów {

        static void przeprowadźWybory(Wybory wybory, MetodaLiczeniaGłosów metodaLiczeniaGłosów) {
            przeprowadźKampanię(wybory, metodaLiczeniaGłosów);
            Głosowanie głosowanie = przeprowadźGłosowanie(wybory, metodaLiczeniaGłosów);
            głosowanie.przeliczGłosy();
        }

        private static void przeprowadźKampanię(Wybory wybory,
                                                MetodaLiczeniaGłosów metodaLiczeniaGłosów) {
            List<OkręgWyborczy> okręgiGłówne = wybory.okręgiGłówne();

            DaneKampanii daneKampanii = new DaneKampanii
                    (asList(wybory.działaniaKampanijne), okręgiGłówne, metodaLiczeniaGłosów);

            Predicate<Partia> nieWykonałaŻadnegoDziałania =
                    partia -> !partia.wykonajDziałanieWRamachKampanii(daneKampanii);

            List<Partia> partieWKampanii = new LinkedList<>(asList(wybory.partie));

            while (!partieWKampanii.isEmpty())
                partieWKampanii.removeIf(nieWykonałaŻadnegoDziałania);
        }

        private static Głosowanie przeprowadźGłosowanie(Wybory wybory,
                                                  MetodaLiczeniaGłosów metodaLiczeniaGłosów) {
            List<OkręgWyborczy> okręgiGłówne = wybory.okręgiGłówne();

            Głosowanie głosowanie = new Głosowanie(metodaLiczeniaGłosów);
            głosowanie.przeprowadźGłosowanieWOkręgach(okręgiGłówne);

            return głosowanie;
        }
    }

    //służy do wczytywania danych wyborów z pliku
    static class WczytywaczDanych {

        static void wczytajWyboryZPliku(Wybory wybory, File plik)
                throws FileReadingException, FileNotFoundException {

            Scanner sc = new Scanner(plik);
            //dodatkowo ignoruje nawiasy i przecinki na wejściu
            sc.useDelimiter("[\\p{javaWhitespace}(),]+");

            try (sc) {
                wczytajLiczbyObiektów(wybory, sc);
                wczytajScalenia(wybory, sc);
                wczytajPartie(wybory, sc);
                wczytajLiczbyWyborcówWOkręgach(wybory, sc);
                wczytajKandydatów(wybory, sc);
                wczytajWyborców(wybory, sc);
                wczytajDziałaniaKampanijne(wybory, sc);
            } catch (Exception e) {
                throw new FileReadingException(e);
            }
        }

        //pierwszy wiersz wejścia
        private static void wczytajLiczbyObiektów(Wybory wybory, Scanner sc) {
            wybory.okręgiWyborcze = new OkręgWyborczy[sc.nextInt()];
            wybory.partie = new Partia[sc.nextInt()];
            wybory.działaniaKampanijne = new DziałanieKampanijne[sc.nextInt()];
            wybory.liczbaCechKandydatów = sc.nextInt();
        }

        //drugi wiersz wejścia
        private static void wczytajScalenia(Wybory wybory, Scanner sc) {
            int liczbaScaleń = sc.nextInt();

            //@todo Rozważyć coś innego zamiast ArrayList
            wybory.scalenia = new ArrayList<>(liczbaScaleń);

            for (int i = 0; i < liczbaScaleń; i++)
                wybory.scalenia.add(new Para<>(sc.nextInt(), sc.nextInt()));
        }

        //trzeci, czwarty i piąty wiersz wejścia
        private static void wczytajPartie(Wybory wybory, Scanner sc)
                throws UnknownCampaignStrategyException {

            String[] nazwy = new String[wybory.partie.length];
            int[] budżety = new int[wybory.partie.length];
            char[] strategie = new char[wybory.partie.length];

            for (int i = 0; i < wybory.partie.length; i++)
                nazwy[i] = sc.next();

            for (int i = 0; i < wybory.partie.length; i++)
                budżety[i] = sc.nextInt();

            for (int i = 0; i < wybory.partie.length; i++)
                strategie[i] = sc.next().charAt(0);

            for (int i = 0; i < wybory.partie.length; i++) {
                StrategiaKampanii strategia;
                char znak = strategie[i];

                if (znak == 'R') {
                    strategia = new StrategiaZRozmachem();
                } else if (znak == 'S') {
                    strategia = new StrategiaSkromna();
                } else if (znak == 'W') {
                    strategia = new StrategiaWłasna();
                } else if (znak == 'Z') {
                    strategia = new StrategiaZachłanna();
                } else {
                    throw new UnknownCampaignStrategyException(znak);
                }

                wybory.partie[i] = new Partia(nazwy[i], budżety[i], strategia);
            }
        }

        //szósty wiersz wejścia
        private static void wczytajLiczbyWyborcówWOkręgach(Wybory wybory, Scanner sc) {
            for (int i = 0; i < wybory.okręgiWyborcze.length; i++)
                wybory.okręgiWyborcze[i] =
                        new OkręgWyborczy(i + 1, sc.nextInt());
        }

        private static WektorOgraniczony wczytajWektor
                (int rozmiar, int ograniczenie, Scanner sc) {

            WektorOgraniczony w = new WektorOgraniczony(rozmiar, ograniczenie);

            for (int i = 0; i < rozmiar; i++)
                w.ustawWspółrzędną(i, sc.nextInt());

            return w;
        }

        private static Partia znajdźPartięONazwie(Wybory wybory, String nazwa)
                throws UnknownPoliticalPartyException {

            for (Partia partia : wybory.partie) {
                if (partia.nazwa().equals(nazwa))
                    return partia;
            }

            throw new UnknownPoliticalPartyException(nazwa);
        }

        private static void wczytajKandydatów(Wybory wybory, Scanner sc) {
            for (OkręgWyborczy okręgWyborczy : wybory.okręgiWyborcze) {
                for (Partia partia : wybory.partie) {
                    for (int i = 0; i < okręgWyborczy.liczbaMandatów(false); i++) {

                        String imię = sc.next();
                        String nazwisko = sc.next();
                        int numerOkręgu = sc.nextInt();
                        String nazwaPartii = sc.next();
                        int pozycjaNaLiście = sc.nextInt();
                        WektorOgraniczony cechy = wczytajWektor
                                (wybory.liczbaCechKandydatów, 100, sc);

                        assert numerOkręgu == okręgWyborczy.numerOkręgu(false);
                        assert nazwaPartii.equals(partia.nazwa());

                        Kandydat kandydat = new Kandydat(imię, nazwisko,
                                okręgWyborczy, partia, pozycjaNaLiście, cechy);

                        okręgWyborczy.dodajKandydata(kandydat);
                    }
                }
            }
        }

        private static Wyborca utwórzWyborcę
                (String imię, String nazwisko,
                 OkręgWyborczy okręgWyborczy, int typWyborcy,
                 Partia uwielbionaPartia, Kandydat uwielbionyKandydat,
                 WektorOgraniczony wagiCech, int wybranaCecha)
                throws UnknownPoliticalVoterException {

            if (typWyborcy == TYP_ŻELAZNY_ELEKTORAT_PARTYJNY) {
                return new ŻelaznyElektoratPartyjny(imię, nazwisko, okręgWyborczy, uwielbionaPartia);
            } else if (typWyborcy == TYP_ŻELAZNY_ELEKTORAT_KANDYDATA) {
                return new ŻelaznyElektoratKandydata(imię, nazwisko, okręgWyborczy, uwielbionyKandydat);
            } else if (typWyborcy == TYP_MINIMALIZUJĄCY_JEDNOCECHOWY) {
                return new MinimalizującyJednocechowy(imię, nazwisko, okręgWyborczy, wybranaCecha);
            } else if (typWyborcy == TYP_MAKSYMALIZUJĄCY_JEDNOCECHOWY) {
                return new MaksymalizującyJednocechowy(imię, nazwisko, okręgWyborczy, wybranaCecha);
            } else if (typWyborcy == TYP_WSZECHSTRONNY) {
                return new Wszechstronny(imię, nazwisko, okręgWyborczy, wagiCech);
            } else if (typWyborcy == TYP_MINIMALIZUJĄCY_JEDNOCECHOWOPARTYJNY) {
                return new MinimalizującyJednocechowopartyjny(imię, nazwisko, okręgWyborczy, uwielbionaPartia, wybranaCecha);
            } else if (typWyborcy == TYP_MAKSYMALIZUJĄCY_JEDNOCECHOWOPARTYJNY) {
                return new MaksymalizującyJednocechowopartyjny(imię, nazwisko, okręgWyborczy, uwielbionaPartia, wybranaCecha);
            } else if (typWyborcy == TYP_WSZECHSTRONNY_JEDNOPARTYJNY) {
                return new WszechstronnyJednopartyjny(imię, nazwisko, okręgWyborczy, uwielbionaPartia, wagiCech);
            } else {
                throw new UnknownPoliticalVoterException(typWyborcy);
            }
        }

        private static void wczytajWyborców(Wybory wybory, Scanner sc)
                throws UnknownPoliticalVoterException, UnknownPoliticalPartyException,
                NoSuchPoliticalCandidateException {

            for (OkręgWyborczy okręgWyborczy : wybory.okręgiWyborcze) {
                for (int i = 0; i < okręgWyborczy.liczbaWyborców(false); i++) {
                    String imię = sc.next();
                    String nazwisko = sc.next();
                    int numerOkręgu = sc.nextInt();
                    int typWyborcy = sc.nextInt();

                    Partia partia = null;
                    Kandydat kandydat = null;
                    WektorOgraniczony wagiCech = null;
                    int wybranaCecha = -1;

                    if (wszechstronny(typWyborcy))
                        wagiCech = wczytajWektor(wybory.liczbaCechKandydatów, 100, sc);

                    if (jednocechowy(typWyborcy))
                        wybranaCecha = sc.nextInt();

                    if (jednopartyjny(typWyborcy) || jednokandydatowy(typWyborcy)) {
                        partia = znajdźPartięONazwie(wybory, sc.next());

                        if (jednokandydatowy(typWyborcy)) {
                            int pozycjaNaLiście = sc.nextInt();
                            kandydat = okręgWyborczy.znajdźKandydata(partia, pozycjaNaLiście);

                            if (kandydat == null)
                                throw new NoSuchPoliticalCandidateException(partia, pozycjaNaLiście);
                        }
                    }

                    Wyborca wyborca = utwórzWyborcę
                            (imię, nazwisko, okręgWyborczy, typWyborcy,
                                    partia, kandydat, wagiCech, wybranaCecha);

                    okręgWyborczy.dodajWyborcę(wyborca);
                }
            }
        }

        private static void wczytajDziałaniaKampanijne(Wybory wybory, Scanner sc) {
            for (int i = 0; i < wybory.działaniaKampanijne.length; i++) {
                WektorOgraniczony zmianyWag =
                        wczytajWektor(wybory.liczbaCechKandydatów, 10, sc);
                wybory.działaniaKampanijne[i] = new DziałanieKampanijne(zmianyWag);
            }
        }
    }
}

