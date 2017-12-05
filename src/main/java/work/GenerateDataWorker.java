package work;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class GenerateDataWorker extends SwingWorker<GenerationReport, Double> {

    private int count;
    private Coordinate topLeft, topRight, botLeft, botRight;
    private List<GenerateDataWorkerListener> listeners;
    private boolean running;

    public GenerateDataWorker(Coordinate topLeft, Coordinate topRight, Coordinate botLeft, Coordinate botRight, int count) {
        this.count = count;
        listeners = new ArrayList<>();
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.botLeft = botLeft;
        this.botRight = botRight;
        this.running = false;
    }

    @Override
    protected GenerationReport doInBackground() throws Exception {
        running = true;
        GenerationReport report = new GenerationReport();
        generate(report);
        return report;
    }

    @Override
    protected void done() {
        GenerationReport endReport = null;
        try {
            endReport = get();
        } catch (Exception e) {
            endReport = new GenerationReport();
        } finally {
            if (endReport != null)
                issueEndSuccess(endReport);
        }
    }

    @Override
    protected void process(List<Double> chunks) {
        if (!chunks.isEmpty()) {
            updateProgress(chunks.get(chunks.size() - 1));
        }
    }

    public void cancel() {
        cancel(false);
        running = false;
    }

    public void registerListener(GenerateDataWorkerListener listener) {
        listeners.add(listener);
    }

    private void generate(GenerationReport report) {
        Path2D path2D = this.build(topLeft, topRight, botLeft, botRight);
        List<SpectrumSignalStrength> points = new ArrayList<>();

        while (points.size() < count) {
            if (!running) {
                return;
            }
            Point.Double pointDouble = this.generatePoint(path2D);
            Coordinate coordinate = new Coordinate(pointDouble.x, pointDouble.y);
            if (coordinate.isValid()) {
                SpectrumSignalStrength e = SpectrumSignalStrength.generateSignalStrengthAt(coordinate, -85, -45);
                points.add(e);
                report.markDataPointAsProcessed();

                try {
                    // Thread.sleep(100);
                } catch (Exception ex) {};
                publish(((double)points.size() / (double)count) * 100);
            }
        }

        if (!running) {
            report.setError(false);
            issueCancel(report);
        } else {
            report.setComplete(true);
            report.setStrengthList(points);
            report.setError(false);
            issueEndSuccess(report);
        }
    }

    private void issueCancel(GenerationReport report) {
        for (GenerateDataWorkerListener listener : listeners) {
            listener.onGenerationInterrupt(report);
        }
    }

    private synchronized void updateProgress(double progress) {
        for (GenerateDataWorkerListener listener : listeners) {
            listener.onGenerationProgress(progress);
        }
    }

    private void issueEndSuccess(GenerationReport report) {
        for (GenerateDataWorkerListener listener : listeners) {
            listener.onGenerationComplete(report);
        }
    }

    private void issueEndFailure(GenerationReport report) {
        for (GenerateDataWorkerListener listener : listeners) {
            listener.onGenerationError(report);
        }
    }

    private Point.Double generatePoint(Path2D path2D) {
        Rectangle bounds = path2D.getBounds();
        double x, y;
        do {
            x = bounds.getX() + bounds.getWidth() * Math.random();
            y = bounds.getY() + bounds.getHeight() * Math.random();
        } while (!path2D.contains(x, y));
        return new Point.Double(x, y);
    }

    private Path2D build(Coordinate... coordinates) {
        Path2D path2D = new Path2D.Double();
        for (int i = 0; i < coordinates.length; i++) {
            Coordinate coordinate = coordinates[i];
            if (i == 0) {
                path2D.moveTo(coordinate.getLatitude(), coordinate.getLongitude());
            } else {
                path2D.lineTo(coordinate.getLatitude(), coordinate.getLongitude());
            }
        }
        path2D.closePath();
        return path2D;
    }

}
