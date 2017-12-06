package ui;

import ui.abstractuiclasses.JCustomPanel;
import ui.abstractuiclasses.JSetupListenerPanel;
import work.objects.Coordinate;
import work.objects.ReadingTimeRange;
import work.objects.TowerCarrier;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GenerateSignalStrengthDataPanel extends JCustomPanel {

    private JSetupListenerPanel.OnJFrameActionsListener listener;

    public GenerateSignalStrengthDataPanel(JSetupListenerPanel.OnJFrameActionsListener listener) {
        this.listener = listener;
        add(new LocationsCountInputPanel());
        //add(new LocationsCoordinatesInputPanel());
        add(new TimeRangeSelectionPanel());
        add(new TowerCarrierSelectionPanel());
    }

    @Override
    public boolean hasValidInput() {
        return false;
    }

    private class LocationsCountInputPanel extends JSetupListenerPanel {
        private JLabel text;
        private JTextField locationsCount;
        private String count;

        @Override
        protected void setupListeners() {
            text = new JLabel("How many locations?");
            locationsCount = new JTextField();

            locationsCount.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    count = locationsCount.getText();
                }
            });
            add(text);
            add(locationsCount);
        }

        @Override
        public boolean hasValidInput() {
            try {
                return Integer.parseInt(count) > 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public int getLocationsCount() {
            checkValidInput();
            return Integer.parseInt(count);
        }
    }

    private class LocationsCoordinatesInputPanel extends JSetupListenerPanel {
        @Override
        protected void setupListeners() {

        }

        public List<Coordinate> getCoordinatesList() {
            checkValidInput();
            List<Coordinate> coordinates = new ArrayList<>();

            return coordinates;
        }

        @Override
        public boolean hasValidInput() {
            return false;
        }
    }

    private class TimeRangeSelectionPanel extends JSetupListenerPanel {
        private JSlider timeRangeSlider;
        private JLabel text, status;
        private ReadingTimeRange timeRange;

        @Override
        protected void setupListeners() {
            text = new JLabel("Time range: ");
            status = new JLabel();
            timeRangeSlider = new JSlider();
            timeRangeSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    timeRange = ReadingTimeRange.TIME_RANGES[timeRangeSlider.getValue()];
                    status.setText(timeRange.toString());
                }
            });
            timeRangeSlider.setValue(1);
            timeRangeSlider.setMaximum(ReadingTimeRange.TIME_RANGES.length - 1);
            add(text);
            add(timeRangeSlider);
            add(status);
        }

        public ReadingTimeRange getTimeRange() {
            checkValidInput();
            return ReadingTimeRange.ONE_WEEK;
        }

        @Override
        public boolean hasValidInput() {
            // Input will always be valid here.
            return true;
        }
    }

    private class TowerCarrierSelectionPanel extends JSetupListenerPanel {
        private JLabel text;
        private JComboBox<TowerCarrier> carrierJComboBox;
        private TowerCarrier carrier;

        @Override
        protected void setupListeners() {
            text = new JLabel("Carrier: ");
            carrierJComboBox = new JComboBox<>();
            for (TowerCarrier carrier : TowerCarrier.TOWER_CARRIERS) {
                carrierJComboBox.addItem(carrier);
            }
            carrierJComboBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    carrier = (TowerCarrier) e.getItem();
                }
            });
            add(text);
            add(carrierJComboBox);
        }

        public TowerCarrier getTowerCarrier() {
            checkValidInput();
            return carrier;
        }

        @Override
        public boolean hasValidInput() {
            // Input will always be valid here.
            return true;
        }
    }

    private class ButtonsPanel extends JSetupListenerPanel {
        @Override
        protected void setupListeners() {
            JButton exit = new JButton(), cancel = new JButton(), generate = new JButton();

            setJButton(exit, "Exit", new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    GenerateSignalStrengthDataPanel.this.listener.onExit();
                }
            });
            setJButton(cancel, "Cancel", new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    GenerateSignalStrengthDataPanel.this.listener.onCancel();
                }
            });
            setJButton(generate, "Generate!", new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    GenerateSignalStrengthDataPanel.this.listener.onSubmit();
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
