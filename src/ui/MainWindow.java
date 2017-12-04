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

    private JFrame frame;
    private boolean shown;

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
        GenerateDataWorker.registerListener(this);
    }

    public void show() {
        if (shown) return;

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Meguri meguru ginga no you ni, sukiyo.........!");
            }
        });
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Generating data...");
                GenerateDataWorker.getInstance().generate(
                        toCoordinate(a4540726475697280TextField),
                        toCoordinate(a4543063675677917TextField),
                        toCoordinate(a4542123875708452TextField),
                        toCoordinate(a4541559275676246TextField)
                );
            }
        });

        frame.setVisible(true);
        shown = true;
    }

    public void onGenerationComplete(GenerationReport report) {
        System.out.print(report.wasAnyGenerated() ? "Compelete." : "Not compelete.");
        System.out.println(" Points generated: " + report.getPointsGenerated());
    }

    public void onGenerationError(GenerationReport report) {

    }

    public void onGenerationInterrupt(GenerationReport report) {

    }

    private Coordinate toCoordinate(JTextField textField) {
        return new Coordinate(textField.getText());
    }
}
