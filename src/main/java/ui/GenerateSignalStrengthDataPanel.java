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

    public GenerateSignalStrengthDataPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
        private JComboBox<ReadingTimeRange> timeRangeComboBox;
        private JLabel text, status;
        private ReadingTimeRange timeRange;

        @Override
        protected void setupListeners() {
            text = new JLabel("Time range: ");
            status = new JLabel();

            timeRangeComboBox = new JComboBox<>();
            for (ReadingTimeRange timeRange : ReadingTimeRange.TIME_RANGES) {
                timeRangeComboBox.addItem(timeRange);
            }

            timeRangeComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    timeRange = (ReadingTimeRange) e.getItem();
                }
            });

            add(text);
            add(timeRangeComboBox);
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
}
