package wybory.pomoce;

import wybory.pomoce.para.Para;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Pomoce {

    public static <T1, T2> List<Para<T1, T2>> iloczynKartezjański(List<T1> A, List<T2> B) {
        List<Para<T1, T2>> iloczyn = new ArrayList<>(A.size() * B.size());

        for (T1 elemA : A)
            for (T2 elemB : B)
                iloczyn.add(new Para<>(elemA, elemB));

        return iloczyn;
    }

    //znajduje najlepsze elementy (o największej wartości) i wybiera losowy z nich
    public static <T> T wybierzNajlepszyLosowy(List<T> elementy, Wartościowanie<T> wartościowanie) {
        int najlepszaWartość = Integer.MIN_VALUE;
        Stack<T> najlepsze = new Stack<>();

        for (T elem : elementy) {
            int wartość = wartościowanie.wartość(elem);

            if (wartość >= najlepszaWartość) {
                if (wartość > najlepszaWartość) {
                    najlepszaWartość = wartość;
                    najlepsze.clear();
                }
                najlepsze.add(elem);
            }
        }

        //wybieranie losowego elementu z najlepszych do zwrócenia
        Random rand = new Random();
        int index = rand.nextInt(najlepsze.size());

        while (index-- > 0)
            najlepsze.pop();

        return najlepsze.isEmpty() ? null : najlepsze.peek();
    }
}
