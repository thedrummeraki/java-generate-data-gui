package work;

public interface GenerateDataWorkerListener {

    void onGenerationProgress(double progress);
    void onGenerationComplete(GenerationReport report);
    void onGenerationInterrupt(GenerationReport report);
    void onGenerationError(GenerationReport report);

}
