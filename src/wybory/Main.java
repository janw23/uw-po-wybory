package wybory;

import wybory.głosowanie.MetodaDHondta;
import wybory.głosowanie.MetodaHareaNiemeyera;
import wybory.głosowanie.MetodaLiczeniaGłosów;
import wybory.głosowanie.MetodaSainteLague;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

//@todo Rozważyć zamianę w plikach LinkedList oraz ArrayList

public class Main {

    public static void main(String[] args) {

        //@todo Try catch jeśli starczy czasu
        //@todo Zmienić na pierwszy argument programu
        File plik = new File("/home/janw23/MIMUW/PO/Projects/uw-po-wybory/test1.in");//args[0]);

        MetodaLiczeniaGłosów[] metodyLiczeniaGłosów =
                {new MetodaDHondta(), new MetodaHareaNiemeyera(), new MetodaSainteLague()};

        try {
            for (var metodaLiczeniaGłosów : metodyLiczeniaGłosów) {
                Wybory wybory = new Wybory(plik);
                String rezultat = wybory.przeprowadźSymulację(metodaLiczeniaGłosów);
                System.out.println(rezultat);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku " + plik.getAbsolutePath());
        } catch (FileReadingException e) {
            System.err.println("Problem z czytaniem pliku: " + e.getCause());
        }
    }
}
