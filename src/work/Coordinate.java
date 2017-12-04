package work;

import java.awt.*;

public class Coordinate {

    private double latitude;
    private double longitude;
    private boolean valid;

    public Coordinate(String coordinate) {
        this(coordinate, ",");
    }

    public Coordinate(String coordinate, String separatorRegex) {
        valid = false;
        if (coordinate != null) {
            String[] parts = coordinate.split(separatorRegex);
            if (parts.length == 2) {
                boolean ok;
                try {
                    latitude = Double.parseDouble(parts[0]);
                    ok = true;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    latitude = 0;
                    ok = false;
                }
                try {
                    longitude = Double.parseDouble(parts[1]);
                    if (ok) ok = true;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    longitude = 0;
                    ok = false;
                }
                valid = ok;
            } else {
                throw new IllegalArgumentException("Please make sure that these coordinates " +
                        "are separated with only one \"" + separatorRegex + "\"");
            }
        }
    }

    public boolean isValid() {
        return valid;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
