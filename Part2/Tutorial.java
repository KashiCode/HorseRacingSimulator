package Part2;

import javax.swing.*;
import java.awt.*;


/**
 * The Class Tutorial instantiates a text area that contains step by step instructions on how to operate the Horse Racing Simulator.  
 *
 * @author Maks Ostrynski
 * @version 1 26/04/2024
 */
public class Tutorial extends JFrame {
    public Tutorial() {
        setTitle("Tutorial");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to the Tutorial", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);
        
        JTextArea tutorialText = new JTextArea();

        tutorialText.setText("Step 1: Press Customise Horse button in the Main menu.\n" +
                     "Step 2: Press the reset Horse button in the customise horse menu to ensure there are no pre-existing Horses.\n" +
                     "Step 3: Fill in the three Horse Fields with the valid information and press the add Horse info button to add your horses to the program.\n" +
                     "Step 4: If you added the wrong information press the reset Horse button to erase your Horse information. Otherwise Press the Continue button.\n" +
                     "Step 5: Change the track distance in the top left corner of the Race screen to your desired size and then press the Start Race Button to begin Racing with your Horses.\n" +
                     "Step 6: Once a race is complete, signified with a message, Press the reset race Button to race again.\n" +
                     "Step 7: Press the Statistics button on the Race screen to view all added Horses and the previous race information.\n" +
                     "Step 8: if you are not satisfied with your Horses you can press the Edit Horses button to go back to the Horse customisation screen.");
        tutorialText.setEditable(false);
        tutorialText.setFont(new Font("Arial", Font.PLAIN, 14));
        tutorialText.setWrapStyleWord(true);
        tutorialText.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(tutorialText);
        add(scrollPane, BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
