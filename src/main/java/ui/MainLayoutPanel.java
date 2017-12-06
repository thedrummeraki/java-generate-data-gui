package ui;

import ui.abstractuiclasses.JCustomPanel;
import ui.abstractuiclasses.JSetupListenerPanel;

import javax.swing.*;
import java.awt.*;

public class MainLayoutPanel extends JSetupListenerPanel {

    private JCustomPanel centralPanel;
    private OnJFrameActionsListener actionsListener;

    public MainLayoutPanel(OnJFrameActionsListener listener) {
        actionsListener = listener;
        setLayout(new BorderLayout(10, 10));
        centralPanel = new GenerateSignalStrengthDataPanel(actionsListener);
        System.out.println("anything?");
        this.add(new JLabel("Generate Signal Strength data"), BorderLayout.NORTH);
        this.add(centralPanel, BorderLayout.CENTER);
        setupListeners();
    }

    @Override
    protected void setupListeners() {
    }

    @Override
    public boolean hasValidInput() {
        return centralPanel.hasValidInput();
    }
}
