package work;

import sun.plugin.dom.core.CoreConstants;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

public class GenerateDataWorker {

    private boolean running;

    private GenerateDataWorker() {
        listeners = new ArrayList<>();
    }

    public void generate(Coordinate topLeft, Coordinate topRight, Coordinate botLeft, Coordinate botRight, int count) {
        running = true;
        GenerationReport report = new GenerationReport();
        report.setError(true);

        Path2D path2D = this.build(topLeft, topRight, botLeft, botRight);
        List<SpectrumSignalStrength> points = new ArrayList<>();

        while (points.size() < count) {
            Point.Double pointDouble = this.generatePoint(path2D);
            Coordinate coordinate = new Coordinate(pointDouble.x, pointDouble.y);
            if (coordinate.isValid()) {
                SpectrumSignalStrength e = SpectrumSignalStrength.generateSignalStrengthAt(coordinate, -85, -45);
                points.add(e);
                System.out.println("New point: " + e.getCoordinates() + " (" + e.getSignalStrength() + " dBm)");
                report.markDataPointAsProcessed();
            }
        }

        report.setError(false);
        issueEndSuccess(report);
        running = false;
    }

    public void cancel() {
        if (running) {
            running = false;
        } else {
            System.err.println("No need to cancel a non-running task.");
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

    private void issueCancel(GenerationReport report) {
        for (GenerateDataWorkerListener listener : listeners) {
            listener.onGenerationInterrupt(report);
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

    private void addListener(GenerateDataWorkerListener listener) {
        listeners.add(listener);
    }

    private List<GenerateDataWorkerListener> listeners;

    private static GenerateDataWorker worker;
    private static final Object lock = new Object();

    public static GenerateDataWorker getInstance() {
        if (worker == null) {
            synchronized (lock) {
                if (worker == null) {
                    worker = new GenerateDataWorker();
                }
            }
        }
        return worker;
    }

    public static void registerListener(GenerateDataWorkerListener listener) {
        getInstance().addListener(listener);
    }

}
