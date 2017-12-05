package work.objects;

import java.util.Date;

public final class ReadingTimeRange {

    private int index;
    private String name;
    private int timeStampsCount;
    private long timeVariation;

    private ReadingTimeRange(int index, String name, int timeStampsCount, long timeVariation) {
        this.index = index;
        this.name = name;
        this.timeVariation = timeVariation;

        if (timeStampsCount < 1) {
            throw new IllegalArgumentException("Invalid time stamp count. Needs to be 1 or more.");
        }
        this.timeStampsCount = timeStampsCount;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public long[] getTimeStamps() {
        long[] timeStamps = new long[timeStampsCount];
        long start = new Date().getTime();
        for (int i = 0; i < timeStampsCount; i++) {
            timeStamps[i] = start;
            start += timeVariation;
        }
        return timeStamps;
    }

    @Override
    public String toString() {
        return name;
    }

    public static final ReadingTimeRange ONE_DAY = new ReadingTimeRange(0, "1 day", 2, 40);
    public static final ReadingTimeRange ONE_WEEK = new ReadingTimeRange(0, "1 week", 14, 100);
    public static final ReadingTimeRange ONE_MONTH = new ReadingTimeRange(0, "1 month", 40, 200);
    public static final ReadingTimeRange ONE_TRIMESTER = new ReadingTimeRange(0, "4 months", 40 * 4, 5000);
    public static final ReadingTimeRange ONE_SEMESTER = new ReadingTimeRange(0, "6 months", 60 * 4, 10000);
    public static final ReadingTimeRange ONE_YEAR = new ReadingTimeRange(0, "1 year", 365 * 2, 20000);

    public static final ReadingTimeRange[] TIME_RANGES = {
          ONE_DAY,
          ONE_WEEK,
          ONE_MONTH,
          ONE_TRIMESTER,
          ONE_SEMESTER,
          ONE_YEAR
    };
}
