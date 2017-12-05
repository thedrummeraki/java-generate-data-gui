package work.objects;

public class DataReadingEntry {

    public static final String[] CSV_HEADERS = {"LATITUDE", "LONGITUDE", "MCC", "MNC", "TIME", "SIGNAL_STRENGTH"};

    private Coordinate coordinates;
    private TowerCarrier carrier;
    private SpectrumSignalStrength signalStrength;
    private long timeStamp;

    public void setCoordinates(Coordinate coordinates) {
        this.coordinates = coordinates;
    }

    public void setCarrier(TowerCarrier carrier) {
        if (carrier.isUndefined()) {
            carrier = TowerCarrier.chooseRandomCarrier();
        }
        this.carrier = carrier;
    }

    public void setSignalStrength(SpectrumSignalStrength signalStrength) {
        this.signalStrength = signalStrength;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String toCSVEntry() {
        StringBuilder sb = new StringBuilder();

        sb.append(coordinates.getLatitude()); sb.append(',');
        sb.append(coordinates.getLongitude()); sb.append(',');
        sb.append(carrier.getMCC()); sb.append(',');
        sb.append(carrier.getMNC()); sb.append(',');
        sb.append(timeStamp); sb.append(',');
        sb.append(signalStrength.getSignalStrength()); sb.append('\n');

        return sb.toString();
    }
}
