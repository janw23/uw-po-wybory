package wybory.osoba;

public abstract class Osoba {

    private String imię;
    private String nazwisko;

    public Osoba(String imię, String nazwisko) {
        this.imię = imię;
        this.nazwisko = nazwisko;
    }

    public final String dajImię() {
        return imię;
    }

    public final String dajNazwisko() {
        return nazwisko;
    }
}
