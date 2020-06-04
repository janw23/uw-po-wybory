package wybory.pomoce.para;

public class Para<F, S> {
    private F first;
    private S second;

    public Para(F pierwszy, S drugi) {
        ustaw(pierwszy, drugi);
    }

    public void pierwszy(F pierwszy) {
        this.first = pierwszy;
    }

    public void drugi(S drugi) {
        this.second = drugi;
    }

    public F pierwszy() {
        return first;
    }

    public S drugi() {
        return second;
    }

    public void ustaw(F pierwszy, S drugi) {
        this.first = pierwszy;
        this.second = drugi;
    }

    public String toString() {
        return "(" + first.toString() + ", " + second.toString() + ")";
    }
}
