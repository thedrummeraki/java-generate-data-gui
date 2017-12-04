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

    public void generate(Coordinate topLeft, Coordinate topRight, Coordinate botLeft, Coordinate botRight) {
        running = true;
        GenerationReport report = new GenerationReport();
        report.setError(true);

        Path2D path2D = this.build(topLeft, topRight, botLeft, botRight);
        PathIterator iterator = path2D.getPathIterator(null);
        double[] coordinates = new double[4];

        while (!iterator.isDone()) {
            int type = iterator.currentSegment(coordinates);
            if (type == PathIterator.WIND_NON_ZERO || type == PathIterator.WIND_EVEN_ODD) {
                System.out.println("Current point: " + coordinates[0] + ", " + coordinates[1]);
                report.markDataPointAsProcessed();
            }
            iterator.next();
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
