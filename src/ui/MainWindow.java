package ui;

import work.Coordinate;
import work.GenerateDataWorker;
import work.GenerateDataWorkerListener;
import work.GenerationReport;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow implements GenerateDataWorkerListener {
    private JPanel mainPanel;
    private JButton exitButton;
    private JButton generateButton;
    private JTextField a4543063675677917TextField;
    private JTextField a4542123875708452TextField;
    private JTextField a4540726475697280TextField;
    private JTextField a4541559275676246TextField;
    private JLabel generationState;
    private JButton cancelButton;

    private int pointsToGenerate;

    private JFrame frame;
    private boolean shown;
    private GenerateDataWorker dataWorker;

    public MainWindow() {
        this(null);
    }

    public MainWindow(String title) {
        frame = new JFrame(title);
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);

        shown = false;

        pointsToGenerate = 100;
    }

    public void show() {
        if (shown) return;

        exitButton.addActionListener(e -> {
            if (dataWorker != null) {
                dataWorker.cancel();
            }
            frame.dispose();
        });
        cancelButton.addActionListener(e -> {
            if (dataWorker != null) {
                dataWorker.cancel();
            }
        });
        generateButton.addActionListener(e -> {
            System.out.println("Generating data...");
            generateButton.setEnabled(false);
            cancelButton.setEnabled(true);
            generateButton.setText("Please wait...");
            SwingUtilities.invokeLater(this::runGeneration);
        });

        frame.setVisible(true);
        shown = true;
    }

    @Override
    public synchronized void onGenerationProgress(double progress) {
        generationState.setText("Generation data... (" + (int)progress + "% complete)");
    }

    public void onGenerationComplete(GenerationReport report) {
        generateButton.setEnabled(true);
        cancelButton.setEnabled(false);
        generateButton.setText("Again!");
        generationState.setText("Done. Points generated: " + report.getPointsGenerated());
    }

    public void onGenerationError(GenerationReport report) {
        generateButton.setEnabled(true);
        cancelButton.setEnabled(false);
        generateButton.setText("Try again");

    }

    public void onGenerationInterrupt(GenerationReport report) {
        generateButton.setEnabled(true);
        cancelButton.setEnabled(false);
        generateButton.setText("Restart");
        generationState.setText("Generation interrupted...");
    }

    private Coordinate toCoordinate(JTextField textField) {
        return new Coordinate(textField.getText());
    }

    private void runGeneration() {
        dataWorker = new GenerateDataWorker(toCoordinate(a4540726475697280TextField),
                toCoordinate(a4543063675677917TextField),
                toCoordinate(a4542123875708452TextField),
                toCoordinate(a4541559275676246TextField),
                pointsToGenerate
        );
        dataWorker.registerListener(this);
        dataWorker.execute();
    }
}
