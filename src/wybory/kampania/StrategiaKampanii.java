package wybory.kampania;

public abstract class StrategiaKampanii {

    public static final int STRATEGIA_Z_ROZMACHEM = 1;
    public static final int STRATEGIA_SKROMNA = 2;
    public static final int STRATEGIA_ZACHŁANNA = 3;
    public static final int STRATEGIA_WŁASNA = 4; //@todo Zmienić nazwę?

    //@todo usunąć
    /*public static StrategiaKampanii utwórzStrategięKampanii(int kod)
            throws UnknownCampaignStrategyException {

        switch (kod) {
            case STRATEGIA_Z_ROZMACHEM:
                return new StrategiaZRozmachem();
            case STRATEGIA_SKROMNA:
                return new StrategiaSkromna();
            case STRATEGIA_ZACHŁANNA:
                return new StrategiaZachłanna();
            case STRATEGIA_WŁASNA:
                return new StrategiaWłasna();
            default:
                throw new UnknownCampaignStrategyException(kod);
        }
    }*/

}
