package ui;

import work.*;

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
    private GenerationReport successReport;

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

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dataWorker != null) {
                    dataWorker.cancel();
                }
                frame.dispose();
            }
        });
        setCancelButtonAction(false);
        generateButton.addActionListener(generateActionListener());

        frame.setVisible(true);
        shown = true;
    }

    private ActionListener generateActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Generating data...");
                generateButton.setEnabled(false);
                cancelButton.setEnabled(true);
                generateButton.setText("Please wait...");
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        runGeneration();
                    }
                });
            }
        };
    }

    @Override
    public synchronized void onGenerationProgress(double progress) {
        generationState.setText("Generation data... (" + (int)progress + "% complete)");
    }

    public void onGenerationComplete(GenerationReport report) {
        if (report == null || !report.isComplete()) {
            onGenerationInterrupt(report);
            return;
        }
        successReport = report;
        generateButton.setEnabled(true);
        cancelButton.setEnabled(false);
        generationState.setText("Done. Points generated: " + report.getPointsGenerated());
        if (report.isComplete())
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
                Integer.parseInt(a10000TextField.getText())
        );
        dataWorker.registerListener(this);
        dataWorker.execute();
    }

    private void transformGenerateButtonToSaveButton() {
        generateButton.setText("Save as CSV...");

        setActionListener(generateButton, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Documents");
                int returnValue = fileChooser.showSaveDialog(mainPanel);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (successReport != null && !successReport.isError()) {
                        File file = fileChooser.getSelectedFile();
                        System.out.println("Saving file to " + file);

                        SpectrumSignalStrength.getSignalStrengthAsCSV(successReport.getStrengthList(), file);
                        JOptionPane.showMessageDialog(mainPanel, "Your file was saved at: " + file);
                        cancelButton.setText("Cancel");
                        setActionListener(cancelButton, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (dataWorker != null) {
                                    dataWorker.cancel();
                                }
                            }
                        });
                    }
                } else {
                    setCancelButtonAction(true);
                }
            }
        });
    }

    private void setCancelButtonAction(boolean reset) {
        cancelButton.setEnabled(reset);
        if (reset) {
            cancelButton.setText("Reset");
            setActionListener(cancelButton, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    generateButton.setText("Generate!");
                    setActionListener(generateButton, generateActionListener());
                    setCancelButtonAction(false);
                }
            });
        } else {
            cancelButton.setText("Cancel");
            setActionListener(cancelButton, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (dataWorker != null) {
                        dataWorker.cancel();
                    }
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
