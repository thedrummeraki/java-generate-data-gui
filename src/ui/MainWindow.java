package ui;

import work.Coordinate;
import work.GenerateDataWorker;
import work.GenerateDataWorkerListener;
import work.GenerationReport;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
    private JTextField a10000TextField;

    private int pointsToGenerate;

    private JFrame frame;
    private boolean shown;
    private GenerateDataWorker dataWorker;

    public MainWindow() {
        this(null);
    }

    public MainWindow(String title) {
        frame = new JFrame(title);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
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
        setCancelButtonAction(false);
        generateButton.addActionListener(generateActionListener());

        frame.setVisible(true);
        shown = true;
    }

    private ActionListener generateActionListener() {
        return e -> {
            System.out.println("Generating data...");
            generateButton.setEnabled(false);
            cancelButton.setEnabled(true);
            generateButton.setText("Please wait...");
            SwingUtilities.invokeLater(this::runGeneration);
        };
    }

    @Override
    public synchronized void onGenerationProgress(double progress) {
        generationState.setText("Generation data... (" + (int)progress + "% complete)");
    }

    public void onGenerationComplete(GenerationReport report) {
        generateButton.setEnabled(true);
        cancelButton.setEnabled(false);
        generationState.setText("Done. Points generated: " + report.getPointsGenerated());
        transformGenerateButtonToSaveButton();
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

    private void transformGenerateButtonToSaveButton() {
        generateButton.setText("Save as CSV...");

        setActionListener(generateButton, e -> {
            final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Documents");
            int returnValue = fileChooser.showSaveDialog(mainPanel);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                System.out.println("Saving file to " + file);
            } else {
                setCancelButtonAction(true);
            }
        });
    }

    private void setCancelButtonAction(boolean reset) {
        cancelButton.setEnabled(reset);
        if (reset) {
            cancelButton.setText("Reset");
            setActionListener(cancelButton, e -> {
                generateButton.setText("Generate!");
                setActionListener(generateButton, generateActionListener());
                setCancelButtonAction(false);
            });
        } else {
            cancelButton.setText("Cancel");
            setActionListener(cancelButton, e -> {
                if (dataWorker != null) {
                    dataWorker.cancel();
                }
            });
        }
    }

    private void setActionListener(JButton component, ActionListener listener) {
        if (listener == null) return;

        if (component != null) {
            for (ActionListener l : component.getActionListeners()) {
                component.removeActionListener(l);
            }
            component.addActionListener(listener);
        }
    }
}
