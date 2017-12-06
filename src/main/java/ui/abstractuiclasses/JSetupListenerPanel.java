package ui.abstractuiclasses;

import javax.swing.*;
import java.awt.*;

public abstract class JSetupListenerPanel extends JCustomPanel {

    public JSetupListenerPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        setupListeners();
    }

    public JSetupListenerPanel(LayoutManager layout) {
        super(layout);
        setupListeners();
    }

    public JSetupListenerPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        setupListeners();
    }

    public JSetupListenerPanel() {
        setupListeners();
    }

    /**
     * Sets up all listeners necessary for the panel. Called on constructor.
     * */
    protected abstract void setupListeners();

    public interface OnJFrameActionsListener {
        void onExit();
        void onCancel();
        void onSubmit();
    }
}
