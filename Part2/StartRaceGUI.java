package Part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class StartRaceGUI extends JFrame {
    private JTextArea raceDisplay;
    private JButton startButton, resetButton, statsButton, customButton, bettingButton, exitButton;
    private JSpinner distanceSpinner; // Spinner for track distance
    private Race race;

    public StartRaceGUI() {
        // Setup the main frame
        setTitle("Horse Race");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Setup track distance panel
        JPanel distancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        distancePanel.add(new JLabel("Track Distance:"));
        distanceSpinner = new JSpinner(new SpinnerNumberModel(50, 20, 100, 1));
        distancePanel.add(distanceSpinner);
        add(distancePanel, BorderLayout.NORTH); // Add the panel to the top of the frame

        // Setup buttons panel
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start Race");
        resetButton = new JButton("Reset");
        statsButton = new JButton("Statistics");
        customButton = new JButton("Customizations");
        bettingButton = new JButton("Virtual Betting");
        exitButton = new JButton("Exit");

        // Action listeners for buttons
        startButton.addActionListener(this::startRace);
        resetButton.addActionListener(e -> resetRace());
        statsButton.addActionListener(e -> showStatistics());
        customButton.addActionListener(e -> openCustomizations());
        bettingButton.addActionListener(e -> virtualBetting());
        exitButton.addActionListener(e -> System.exit(0));

        // Add buttons to the panel
        buttonPanel.add(startButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(statsButton);
        buttonPanel.add(customButton);
        buttonPanel.add(bettingButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Setup the race display area
        raceDisplay = new JTextArea();
        raceDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        raceDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(raceDisplay);
        add(scrollPane, BorderLayout.CENTER);

        
    }

    private void startRace(ActionEvent e) {
        int trackDistance = (Integer) distanceSpinner.getValue();
    
        // Initialize the race with the track distance
        race = new Race(trackDistance);
        
        // Set the update listener for race updates
        race.setUpdateListener(new RaceUpdateListener() {
            @Override
            public void updateDisplay(String text) {
                SwingUtilities.invokeLater(() -> {
                    if (!race.isFinished()) {
                        // Refresh the display with the new frame
                        raceDisplay.setText(text);
                    } else {
                        // Append the final message without clearing
                        raceDisplay.append(text + "\n");
                    }
                });
            }
        });
    
        // You may want to load horses from a file or add them manually
        // Uncomment the following line if you wish to load horses from a file
        race.loadHorsesFromFile(System.getProperty("user.dir") + File.separator + "horseAttribute.txt");

    
        // Manually add horses to the race for testing
        //race.addHorse(new Horse('A', "Lightning", 0.7), 1);
        //race.addHorse(new Horse('B', "Thunder", 0.7), 2);
        //race.addHorse(new Horse('C', "Storm", 0.8), 3);
    
        // Disable the start button to prevent race restarts while running
        startButton.setEnabled(false);
    
        // Start the race in a separate thread to keep the GUI responsive
        new Thread(race::startRace).start();
    }



    // Placeholder methods for button actions
    private void resetRace() {
        race.resetRace(); // Reset the race state
        startButton.setEnabled(true); // Enable the start button again
        raceDisplay.setText(""); // Clear the race display
    }

    private void showStatistics() {
            Statistics StatisticsWindow = new Statistics(); 
            StatisticsWindow.setVisible(true);
    }

    private void openCustomizations() {
        CustomiseHorse CustomiseHorseWindow = new CustomiseHorse(this); 
        CustomiseHorseWindow.setVisible(true);
        StartRaceGUI.this.dispose(); //fix this
    }
   

    private void virtualBetting() {
        JOptionPane.showMessageDialog(this, "Not Implemented Yet.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartRaceGUI().setVisible(true));
    }
}