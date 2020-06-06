package wybory.pomoce;

public class Pomoce {

    public static void assessNotNull(Object o) throws NullPointerException {
        if (o == null)
            throw new NullPointerException();
    }
}
