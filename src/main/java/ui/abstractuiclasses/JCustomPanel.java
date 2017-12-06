package ui.abstractuiclasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class JCustomPanel extends JPanel {

    public JCustomPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public JCustomPanel(LayoutManager layout) {
        super(layout);
    }

    public JCustomPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public JCustomPanel() {
    }

    public void setJButton(JButton button, String text, ActionListener listener) {
        button.setText(text);
        for (ActionListener l : button.getActionListeners()) {
            button.removeActionListener(l);
        }
        button.addActionListener(listener);
    }

    /**
     * Checks if the input is valid.
     * */
    public abstract boolean hasValidInput();


    protected final void checkValidInput() {
        if (!hasValidInput()) {
            throw new IllegalStateException("Illegal state: invalid input for " + getClass().getSimpleName());
        }
    }

}
