package wybory;

import wybory.głosowanie.MetodaDHondta;
import wybory.głosowanie.MetodaLiczeniaGłosów;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

//@todo Rozważyć zamianę w plikach LinkedList oraz ArrayList

public class Main {

    public static void main(String[] args) {

        //@todo Try catch jeśli starczy czasu
        //@todo Zmienić na pierwszy argument programu
        File plik = new File("/home/janw23/MIMUW/PO/Projects/uw-po-wybory/test2.in");//args[0]);

        //@todo Napisać kolejne metody liczenia głosów

        try {
            Wybory wybory = new Wybory(plik);
            wybory.symulujWybory(Arrays.asList(new MetodaLiczeniaGłosów[]{new MetodaDHondta()}));
            //wypisz rezultat

        } catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku " + plik.getAbsolutePath());
        } catch (FileReadingException e) {
            System.err.println("Problem z czytaniem pliku: " + e.getCause());
        }
    }
}
