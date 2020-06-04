package wybory;

import wybory.kampania.*;
import wybory.partia.Partia;
import wybory.pomoce.para.Para;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

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

//główna klasa służąca do wczytania danych i
//symulacji procesu wyborczego
public class Wybory {

    private OkręgWyborczy[] okręgiWyborcze;
    private Partia[] partie;
    private DziałanieKampanijne[] działaniaKampanijne;
    private int liczbaCechKandydatów;
    private Collection<Para<Integer, Integer>> scalenia;

    public Wybory(File plik)
            throws FileReadingException, FileNotFoundException {
        super();
        WczytywaczDanych.wczytajWyboryZPliku(this, plik);
    }

    public void symulujWybory() {
        //@todo zrobienie kopii wyborów, żeby zachować dane?
    }

    //służy do wczytywania danych wyborów z pliku
    static class WczytywaczDanych {

        static void wczytajWyboryZPliku(Wybory wybory, File plik)
                throws FileReadingException, FileNotFoundException {

            Scanner sc = new Scanner(plik);
            //dodatkowo ignoruje nawiasy i przecinki na wejściu
            sc.useDelimiter("[\\p{javaWhitespace}(),]+");

            try {
                wczytajLiczbyObiektów(wybory, sc);
                wczytajScalenia(wybory, sc);
                wczytajPartie(wybory, sc);
                wczytajLiczbyWyborcówWOkręgach(wybory, sc);
            } catch (Exception e) {
                throw new FileReadingException(e);
            } finally {
                sc.close();
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
    }
}

