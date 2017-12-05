package work;

import work.objects.DataReadingEntry;
import work.objects.SpectrumSignalStrength;

import java.util.List;

public final class GenerationReport {

    private int pointsGenerated;
    private boolean error;
    private boolean complete;
    private List<DataReadingEntry> dataReadingEntries;

    public GenerationReport() {
        setError(true);
    }

    public int getPointsGenerated() {
        return pointsGenerated;
    }

    public boolean wasAnyGenerated() {
        return pointsGenerated > 0;
    }

    public boolean isError() {
        return error;
    }

    public boolean isComplete() {
        return complete;
    }

    void setError(boolean error) {
        this.error = error;
    }

    void setComplete(boolean complete) {
        this.complete = complete;
    }

    void markDataPointAsProcessed() {
        pointsGenerated++;
    }

    void setDataReadingsList(List<DataReadingEntry> dataReadingEntries) {
        this.dataReadingEntries = dataReadingEntries;
    }

    public List<DataReadingEntry> getDataReadingEntries() {
        return dataReadingEntries;
    }
}
