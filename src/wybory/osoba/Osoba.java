package wybory.osoba;

import java.util.Objects;

public abstract class Osoba {

    protected String imię;
    protected String nazwisko;

    public Osoba(String imię, String nazwisko) {
        Objects.requireNonNull(imię);
        Objects.requireNonNull(nazwisko);
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
