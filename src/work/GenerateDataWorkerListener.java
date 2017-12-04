package work;

public interface GenerateDataWorkerListener {

    void onGenerationComplete(GenerationReport report);
    void onGenerationInterrupt(GenerationReport report);
    void onGenerationError(GenerationReport report);

}
