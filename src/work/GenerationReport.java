package work;

import java.util.List;

public final class GenerationReport {

    private int pointsGenerated;
    private boolean error;
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

    void setError(boolean error) {
        this.error = error;
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
