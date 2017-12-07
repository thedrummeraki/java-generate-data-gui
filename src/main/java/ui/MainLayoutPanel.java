package ui;

import ui.abstractuiclasses.JCustomPanel;
import ui.abstractuiclasses.JSetupListenerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLayoutPanel extends JSetupListenerPanel {

    private JCustomPanel centralPanel;
    private OnJFrameActionsListener actionsListener;

    public MainLayoutPanel(OnJFrameActionsListener listener) {
        super();
        actionsListener = listener;
    }

    @Override
    protected void setupListeners() {
        setLayout(new BorderLayout(10, 10));
        centralPanel = new GenerateSignalStrengthDataPanel();

        JLabel panelTitleLabel = new JLabel("Generate Signal Strength data");
        panelTitleLabel.setHorizontalTextPosition(SwingConstants.CENTER);

        this.add(panelTitleLabel, BorderLayout.NORTH);
        this.add(centralPanel, BorderLayout.CENTER);
        this.add(new ButtonsPanel(), BorderLayout.SOUTH);
    }

    @Override
    public boolean hasValidInput() {
        return centralPanel.hasValidInput();
    }

    private class ButtonsPanel extends JSetupListenerPanel {
        @Override
        protected void setupListeners() {
            JButton exit = new JButton(), cancel = new JButton(), generate = new JButton();

            setJButton(exit, "Exit", new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actionsListener.onExit();
                }
            });
            setJButton(cancel, "Cancel", new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actionsListener.onCancel();
                }
            });
            setJButton(generate, "Generate!", new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actionsListener.onSubmit();
                }
            });
            add(exit);
            add(cancel);
            add(generate);
        }

        @Override
        public boolean hasValidInput() {
            // No input, so always valid input
            return true;
        }
    }

}
