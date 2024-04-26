package Part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;



/**
 * The Class StartRaceGUI contains the visual components for displaying the racing of Horses in the Horse Racing Simulator. 
 * The Class ensures that the Horse Attributes are retrieved and passed correctly to the Race class. 
 * The Class ensures the race displays properly for the user. 
 * 
 * @author Maks Ostrynski
 * @version 2  26/04/2024
 */
public class StartRaceGUI extends JFrame {
    private JTextArea raceDisplay;
    private JButton startButton, resetButton, statsButton, customButton, bettingButton, exitButton;
    private JSpinner distanceSpinner; 
    private Race race;


    //Sets the visual layout of the Horse Racing simulator screen. 
    public StartRaceGUI() {
        setTitle("Horse Race");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

   
        JPanel distancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        distancePanel.add(new JLabel("Track Distance:"));
        distanceSpinner = new JSpinner(new SpinnerNumberModel(50, 20, 100, 1));
        distancePanel.add(distanceSpinner);
        add(distancePanel, BorderLayout.NORTH); 

        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start Race");
        resetButton = new JButton("Reset Race");
        statsButton = new JButton("Statistics");
        customButton = new JButton("Edit Horses");
        bettingButton = new JButton("Virtual Betting");
        exitButton = new JButton("Exit");

        startButton.addActionListener(this::startRace);
        resetButton.addActionListener(e -> resetRace());
        statsButton.addActionListener(e -> showStatistics());
        customButton.addActionListener(e -> openCustomizations());
        bettingButton.addActionListener(e -> virtualBetting());
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(startButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(statsButton);
        buttonPanel.add(customButton);
        buttonPanel.add(bettingButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        raceDisplay = new JTextArea();
        raceDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        raceDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(raceDisplay);
        add(scrollPane, BorderLayout.CENTER);

        
    }

      /** 
     * The logic for the start Race Button. 
     * The method sets the track distance and contains an event listners for appending the race information frame by frame. 
     * Passes the Attribute file to the Race Class. 
     */
    private void startRace(ActionEvent e) {
        int trackDistance = (Integer) distanceSpinner.getValue();
    
        race = new Race(trackDistance);
        
        race.setUpdateListener(new RaceUpdateListener() {
            @Override
            public void updateDisplay(String text) {
                SwingUtilities.invokeLater(() -> {
                    if (!race.isFinished()) {
                        raceDisplay.setText(text);
                    } else {
                        raceDisplay.append(text + "\n");
                    }
                });
            }
        });
    
        race.loadHorsesFromFile(System.getProperty("user.dir") + File.separator + "horseAttribute.txt");

        //race.addHorse(new Horse('A', "Lightning", 0.7), 1);
        //race.addHorse(new Horse('B', "Thunder", 0.7), 2);
        //race.addHorse(new Horse('C', "Storm", 0.8), 3);

        startButton.setEnabled(false);
 
        new Thread(race::startRace).start();
    }


      /** 
     * The logic for the Reset Button.
     * Renables the Race Button and clears the GUI screen. 
     * Resets the race. 
     */ 
    private void resetRace() {
        race.resetRace(); 
        startButton.setEnabled(true); 
        raceDisplay.setText(""); 
    }


      /** 
     * The logic for the Statistics Button.
     * Opens the Statistics Window.
     */
    private void showStatistics() {
            Statistics StatisticsWindow = new Statistics(); 
            StatisticsWindow.setVisible(true);
    }

      /** 
     * The logic for the Customise Button.
     * Opens the Customise Horse Window.
     */
    private void openCustomizations() {
        CustomiseHorse CustomiseHorseWindow = new CustomiseHorse(this); 
        CustomiseHorseWindow.setVisible(true);
        StartRaceGUI.this.dispose(); //fix this
    }
   

  
    //Logic for virtual Betting Button. 
    private void virtualBetting() {
        JOptionPane.showMessageDialog(this, "Not Implemented Yet.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartRaceGUI().setVisible(true));
    }
}