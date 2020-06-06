package wybory.osoba;

import static wybory.pomoce.Pomoce.assessNotNull;

public abstract class Osoba {

    private String imię;
    private String nazwisko;

    public Osoba(String imię, String nazwisko) {
        assessNotNull(imię);
        assessNotNull(nazwisko);
        this.imię = imię;
        this.nazwisko = nazwisko;
    }

    public final String imię() {
        return imię;
    }

    public final String nazwisko() {
        return nazwisko;
    }
}
