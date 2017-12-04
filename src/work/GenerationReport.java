package work;

public final class GenerationReport {

    private int pointsGenerated;
    private boolean error;

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
}
