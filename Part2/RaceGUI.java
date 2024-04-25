package Part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RaceGUI extends JFrame {
    private JTextArea raceDisplay;
    private JButton startButton;
    private Race race;

    public RaceGUI() {
        // Initialize race with the desired length
        race = new Race(5);
        
        race.setUpdateListener(new RaceUpdateListener() {
            @Override
            public void updateDisplay(String text) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if(text.isEmpty()) {
                            raceDisplay.setText(""); // Clear the display when an empty string is received
                        } else {
                            raceDisplay.append(text); // Append the text (for new frames)
                            raceDisplay.append("\n"); // Add a newline for better readability
                        }
                    }
                });
            }
        });

        // Setup main frame
        setTitle("Horse Race");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Setup text area for the race display
        raceDisplay = new JTextArea();
        raceDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Set monospaced font for alignment
        raceDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(raceDisplay);
        add(scrollPane, BorderLayout.CENTER);

        // Setup start button
        startButton = new JButton("Start Race");
        add(startButton, BorderLayout.SOUTH);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startRace();
            }
        });

        // Other initialization code (if any)
    }

    private void startRace() {
        // Add your horses to the race
        race.addHorse(new Horse('A', "Lightning", 0.7), 1);
        race.addHorse(new Horse('B', "Thunder", 0.7), 2);
        race.addHorse(new Horse('C', "Storm", 0.8), 3);
        
        // Disable the start button to prevent race restarts while running
        startButton.setEnabled(false);

        // Start the race in a separate thread to keep the GUI responsive
        new Thread(() -> race.startRace()).start();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RaceGUI().setVisible(true));
    }
}
