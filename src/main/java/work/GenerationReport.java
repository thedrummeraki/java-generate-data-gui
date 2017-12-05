package work;

import java.util.List;

public final class GenerationReport {

    private int pointsGenerated;
    private boolean error;
    private boolean complete;
    private List<SpectrumSignalStrength> strengthList;

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

    void setStrengthList(List<SpectrumSignalStrength> strengthList) {
        this.strengthList = strengthList;
    }

    public List<SpectrumSignalStrength> getStrengthList() {
        return strengthList;
    }
}
