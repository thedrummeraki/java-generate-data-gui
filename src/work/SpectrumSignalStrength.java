package work;

public class SpectrumSignalStrength {

    private Coordinate coordinates;
    private double signalStrength;

    private SpectrumSignalStrength(Coordinate coordinates) {
        this(coordinates, -113);
    }

    private SpectrumSignalStrength(Coordinate coordinates, double signalStrength) {
        this.coordinates = coordinates;
        this.signalStrength = signalStrength;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public double getSignalStrength() {
        return signalStrength;
    }

    private void generateCurveSignalStrength(double lowest, double highest) {
        if (lowest >= highest) {
            throw new IllegalArgumentException("Lower can't be higher.");
        }
        signalStrength = lowest + Math.random() * (highest - lowest);
    }

    public static SpectrumSignalStrength generateSignalStrengthAt(Coordinate coordinate, double lowest, double highest) {
        SpectrumSignalStrength signalStrength = new SpectrumSignalStrength(coordinate);
        signalStrength.generateCurveSignalStrength(lowest, highest);
        return signalStrength;
    }
}
