package work.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

    public String toCSVEntry() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(coordinates.getLatitude());
        stringBuilder.append(',');
        stringBuilder.append(coordinates.getLongitude());
        stringBuilder.append(',');
        stringBuilder.append(signalStrength);
        stringBuilder.append('\n');

        return stringBuilder.toString();
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

    public static double getSignalStrengthAsCSV(List<DataReadingEntry> dataReadingEntries, File file) {
        if (file == null) {
            throw new IllegalArgumentException("Not null files please.");
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
        double size = 0;
        try {
            PrintWriter pw = new PrintWriter(file);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < DataReadingEntry.CSV_HEADERS.length; i++) {
                String header = DataReadingEntry.CSV_HEADERS[i];
                sb.append(header);
                sb.append(i == DataReadingEntry.CSV_HEADERS.length - 1 ? '\n' : ',');
            }

            for (int i = 0; i < dataReadingEntries.size(); i++) {
                DataReadingEntry dataReadingEntry = dataReadingEntries.get(i);
                sb.append(dataReadingEntry.toCSVEntry());
            }

            pw.write(sb.toString());
            pw.close();
            size = file.length();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.", e);
        }
        return size;
    }
}
