package ui;

import ui.abstractuiclasses.JSetupListenerPanel;
import utils.Utils;
import work.*;
import work.objects.Coordinate;
import work.objects.ReadingTimeRange;
import work.objects.SpectrumSignalStrength;
import work.objects.TowerCarrier;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MainWindow implements GenerateDataWorkerListener, JSetupListenerPanel.OnJFrameActionsListener {
    private JPanel mainPanel;
    private JButton exitButton;
    private JButton generateButton;
    private JTextField coordinate1;
    private JTextField coordinate2;
    private JTextField coordinate3;
    private JTextField coordinate4;
    private JLabel generationState;
    private JButton cancelButton;
    private JTextField a10000TextField;
    private JSlider timeRangeSlider;
    private JComboBox<TowerCarrier> carriersComboBox;
    private JLabel timeRangeState;

    private int pointsToGenerate;

    private JFrame frame;
    private boolean shown;
    private GenerateDataWorker dataWorker;
    private GenerationReport successReport;

    private ReadingTimeRange currentTimeRange;
    private TowerCarrier currentTowerCarrier;

    public MainWindow(String title) {
        frame = new JFrame(title);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        frame.setContentPane(new MainLayoutPanel(this));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(frame.getSize());

        timeRangeSlider.setMaximum(ReadingTimeRange.TIME_RANGES.length-1);
        currentTimeRange = ReadingTimeRange.ONE_WEEK;

        shown = false;
        pointsToGenerate = 1000;
    }

    @Override
    public void onExit() {
        frame.dispose();
    }

    @Override
    public void onCancel() {
        onGenerationInterrupt(null);
    }

    @Override
    public void onSubmit() {

    }

    public void show() {
        if (shown) return;

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dataWorker != null) {
                    dataWorker.cancel();
                }
                frame.setContentPane(new JPanel());
                frame.revalidate();
                frame.repaint();
                // frame.dispose();
            }
        });

        for (TowerCarrier carrier : TowerCarrier.TOWER_CARRIERS) {
            if (currentTowerCarrier == null) currentTowerCarrier = carrier;
            carriersComboBox.addItem(carrier);
        }
        carriersComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                currentTowerCarrier = (TowerCarrier) e.getItem();
            }
        });

        setCancelButtonAction(false);
        generateButton.addActionListener(generateActionListener());
        timeRangeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selected = timeRangeSlider.getValue();
                currentTimeRange = ReadingTimeRange.TIME_RANGES[selected];
                timeRangeState.setText("Time range (" + currentTimeRange.getName() + "*)");
            }
        });
        timeRangeSlider.setValue(1);

        a10000TextField.setText(Integer.toString(pointsToGenerate));
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
        generationState.setText("Generating data... (" + (int)progress + "% complete)");
    }

    public void onGenerationComplete(GenerationReport report) {
        if (report == null || !report.isComplete()) {
            onGenerationInterrupt(report);
            return;
        }
        successReport = report;
        generateButton.setEnabled(true);
        cancelButton.setEnabled(false);
        generationState.setText("Done. Processed " + report.getPointsGenerated() + " point(s).");
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
        dataWorker = new GenerateDataWorker(
                toCoordinate(coordinate1),
                toCoordinate(coordinate2),
                toCoordinate(coordinate3),
                toCoordinate(coordinate4),
                Integer.parseInt(a10000TextField.getText())
        );
        dataWorker.setTimeRange(currentTimeRange);
        dataWorker.setTowerCarrier(currentTowerCarrier);
        dataWorker.registerListener(this);
        dataWorker.execute();
    }

    private void transformGenerateButtonToSaveButton() {
        setCancelButtonAction(true);
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

                        double fileSizeBytes = SpectrumSignalStrength.getSignalStrengthAsCSV(successReport.getDataReadingEntries(), file);
                        String fileSize = Utils.getPrettyFileSize(fileSizeBytes);
                        JOptionPane.showMessageDialog(mainPanel, "Your file was saved at: " + file + ".\nWrote: " + fileSize + ".");
                        setCancelButtonAction(false);
                        generateButton.setText("Generate!");
                        setActionListener(generateButton, generateActionListener());
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
                    if (JOptionPane.showConfirmDialog(mainPanel,"All generated data will be lost. Continue?") == JOptionPane.OK_OPTION) {
                        generateButton.setText("Generate!");
                        generationState.setText("A data was lost. Press \"Generate!\" below to restart.");
                        setActionListener(generateButton, generateActionListener());
                        setCancelButtonAction(false);
                    }
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
