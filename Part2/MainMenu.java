package Part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;




/**
 * The Class MainMenu creates the main entry point for the Horse Racing simulator. 
 * The Class contains buttons linking to the Horse Customisation, Statistics and Tutorial windows.
 * The Class also ensures that the Horse Files are created correctly. 
 * 
 * @author Maks Ostrynski
 * @version 1  24/04/2024
 */
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


        //Button logic that links to the Tutorial window. 
        tutorialButton.addActionListener(e -> {
            Tutorial tutorialWindow = new Tutorial(); 
            tutorialWindow.setVisible(true);
        });

        //Button logic that links to the statistics window. 
        statsButton.addActionListener(e -> {
            Statistics StatisticsWindow = new Statistics(); 
            StatisticsWindow.setVisible(true);
        });

        //Button logic that links to the customisation Window. 
        customizeButton.addActionListener(e -> {
            if (CreateFiles()) {
                JOptionPane.showMessageDialog(this, "Files integrity evaluated sucessfully."); //redit
                CustomiseHorse CustomiseHorseWindow = new CustomiseHorse(this); //link to CustomiseHorse.java
                CustomiseHorseWindow.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to check or create files.");
            }
        });

        //Button Logic to exit the program. 
        exitButton.addActionListener(e -> System.exit(0));

        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    /***
     * Method to Style and create the buttons on the Main menu.
     * Adds a hover effect to the buttons. 
     * 
     */
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

    /***
     * Method to create the horseAttribute and raceResults files. 
     * Ensures that they have been created in the Users local directory. 
     * 
     */
    private boolean CreateFiles() {
        
        //Old Directory Path. 
        //String directoryPath = "I:\\TES\\HorseRace Starter\\";

        //New Directory Path:   
        String directoryPath = System.getProperty("user.dir") + File.separator;

        File horseFile = new File(directoryPath + "horseAttribute.txt");
        File raceFile = new File(directoryPath + "raceResults.txt");
    
        try {
            boolean horseFileCreated = horseFile.createNewFile();
            boolean raceFileCreated = raceFile.createNewFile();
            
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