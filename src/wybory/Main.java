package wybory;

import wybory.głosowanie.MetodaGłosowania;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        //@todo Try catch jeśli starczy czasu
        File plik = new File("/home/janw23/MIMUW/PO/Projects/uw-po-wybory/test1.in");//args[0]);

        try {
            Wybory wybory = new Wybory(plik);
            wybory.symulujWybory(Arrays.asList(new MetodaGłosowania[]{null}));
            //wypisz rezultat

        } catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku " + plik.getAbsolutePath());
        } catch (FileReadingException e) {
            System.err.println("Problem z czytaniem pliku: " + e.getCause());
        }
    }
}
