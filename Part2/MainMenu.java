package Part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Welcome to the Horse Racing Simulator", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        add(buttonPanel, BorderLayout.CENTER);

        JButton customizeButton = createButton("Customise Race");
        JButton statsButton = createButton("View Statistics");
        JButton tutorialButton = createButton("View Tutorial");
        JButton exitButton = createButton("Exit");

        buttonPanel.add(customizeButton);
        buttonPanel.add(statsButton);
        buttonPanel.add(tutorialButton);
        buttonPanel.add(exitButton);

        // Opens the tutorial window.
        tutorialButton.addActionListener(e -> {
            Tutorial tutorialWindow = new Tutorial(); 
            tutorialWindow.setVisible(true);
        });

        // Opens the Statistics Window. 
        statsButton.addActionListener(e -> {
            Statistics StatisticsWindow = new Statistics(); 
            StatisticsWindow.setVisible(true);
        });


        // Opens the Horse Customisation window
        customizeButton.addActionListener(e -> {
            if (CreateFiles()) {
                JOptionPane.showMessageDialog(this, "Files integrity evaluated sucessfully."); //redit
                CustomiseHorse CustomiseHorseWindow = new CustomiseHorse(this); //link to CustomiseHorse.java
                CustomiseHorseWindow.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to check or create files.");
            }
        });

        // Exits the program.
        exitButton.addActionListener(e -> System.exit(0));

        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(Color.BLACK);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(215, 215, 215));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(240, 240, 240));
            }
        });

        return button;
    }

    private boolean CreateFiles() {
        String directoryPath = "I:\\TES\\HorseRace Starter\\";
        File horseFile = new File(directoryPath + "horseAttribute.txt");
        File raceFile = new File(directoryPath + "raceResults.txt");
    
        try {
            boolean horseFileCreated = horseFile.createNewFile();
            boolean raceFileCreated = raceFile.createNewFile();
            
            // Check if both files exist or were created successfully.
            return (horseFileCreated || horseFile.exists()) && (raceFileCreated || raceFile.exists());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu());
    }
}