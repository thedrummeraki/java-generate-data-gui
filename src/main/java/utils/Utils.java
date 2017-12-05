package utils;

public final class Utils {

    private Utils() {}

    public static String getPrettyFileSize(double size) {
        double tester = size;
        if (tester <= 0) {
            return "0 bytes.";
        }
        tester /= 1024;
        if ((long)tester <= 0) {
            return twoDecimals(tester * 1000) + " bytes";
        }
        tester /= 1024;
        if ((long)tester <= 0) {
            return twoDecimals(tester * 1000) + " kB";
        }
        tester /= 1024;
        if ((long)tester <= 0) {
            return twoDecimals(tester * 1000) + " MB";
        }
        tester /= 1024;
        return twoDecimals(tester) + " GB";
    }

    public static String twoDecimals(double number) {
        return String.format("%.2f", number);
    }

}
