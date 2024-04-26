package Part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;


public class CustomiseHorse extends JDialog {
    //private final int HORSE_FIELDS = 6; 

    public CustomiseHorse(JFrame parent) {
        super(parent, "Customise Horse Details", true);
        setSize(600, 800);
        setLayout(new GridLayout(0, 1)); 
        setLocationRelativeTo(parent);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        for (int i = 1; i <= 3; i++) {
            JPanel horsePanel = new JPanel(new GridLayout(0, 2)); // Use GridLayout for label-input pairs
            addHorseComponents(horsePanel, "Horse " + i);
            contentPanel.add(horsePanel);
        }
        
        JScrollPane scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        JButton createButton = new JButton("Add Horse Details");
        createButton.addActionListener(this::writeHorseDetails);

        JButton resetButton = new JButton("Reset Horse Details");
        resetButton.addActionListener(this::resetHorseDetails);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        JButton continueButton = new JButton("Continue to Race");
        continueButton.addActionListener(e -> openRaceCustomisationPage());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(createButton);
        contentPanel.add(buttonPanel);
        buttonPanel.add(resetButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(continueButton);
    
        
        setVisible(true);
    }

    private void addHorseComponents(JPanel panel, String title) {
        panel.add(new JLabel(title));
        panel.add(new JLabel()); // Empty placeholder for alignment
        panel.add(new JLabel("Symbol:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Name:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Confidence:"));
        panel.add(new JSpinner(new SpinnerNumberModel(0.5, 0.1, 0.9, 0.01)));
        panel.add(new JLabel("Color:"));
        panel.add(new JComboBox<>(new String[]{"Black", "Bay", "Chestnut", "Gray"}));
        panel.add(new JLabel("Breed:"));
        panel.add(new JComboBox<>(new String[]{"Thoroughbred", "Quarter Horse", "Arabian", "Appaloosa", "Paint"}));
        panel.add(new JLabel("Accessories:"));
        panel.add(new JComboBox<>(new String[]{"None", "Stirrups", "Girth", "Bridle", "Blinkers", "Saddle"}));
    }

    private void writeHorseDetails(ActionEvent e) {

        if (checkExistingHorses()) {
            JOptionPane.showMessageDialog(this, "Horses already exist, please press the Continue Button");
            return;
        }

        try (
            FileWriter fw = new FileWriter("I:\\TES\\HorseRace Starter\\horseAttribute.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)
        ) {
            Component[] components = getContentPane().getComponents();
            // Obtain the horse panels from the content panel
            JPanel contentPanel = (JPanel) ((JViewport) ((JScrollPane) components[0]).getComponent(0)).getView();
            for (int i = 0; i < contentPanel.getComponentCount() - 1; i++) {
                JPanel horsePanel = (JPanel) contentPanel.getComponent(i);
                Component[] horseComponents = horsePanel.getComponents();
                
                String name = ((JTextField)horseComponents[3]).getText().trim();
                String symbol = ((JTextField)horseComponents[5]).getText().trim();
                double confidence = (double)((JSpinner)horseComponents[7]).getValue();
                String color = (String)((JComboBox)horseComponents[9]).getSelectedItem();
                String breed = (String)((JComboBox)horseComponents[11]).getSelectedItem();
                String accessories = (String)((JComboBox)horseComponents[13]).getSelectedItem();
                
                // Validate input before writing to file
                if (!name.isEmpty() && !symbol.isEmpty()) {   
                    out.println(name + "," + symbol + "," + confidence + "," + color + "," + breed + "," + accessories);
                } else {
                    // Handle invalid input appropriately
                    JOptionPane.showMessageDialog(this, "Please fill all fields with valid information for each horse.");
                    return; // Stop the process if there is invalid input
                }
            }
            JOptionPane.showMessageDialog(this, "Horses created successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to create horses.");
        }
        dispose(); // Close the dialog after saving the data
    }

    private boolean checkExistingHorses() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("I:\\TES\\HorseRace Starter\\horseAttribute.txt"))) {
            while (reader.readLine() != null && count < 3) {
                count++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return count >= 3;
    }

    private void resetHorseDetails(ActionEvent e) {
        int dialogResult = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to erase all horse details?",
                "Reset Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (dialogResult == JOptionPane.YES_OPTION) {
            // Erase the horse details from the file
            try (PrintWriter writer = new PrintWriter("I:\\TES\\HorseRace Starter\\horseAttribute.txt")) {
                // Just opening the file with a PrintWriter will erase its contents
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to reset horses.");
            }
        }
    }

    private void openRaceCustomisationPage() {
    // Check if the horse file contains entries before opening the race customisation page
    if (!checkExistingHorses()) {
        // Display message if no horses are found
        JOptionPane.showMessageDialog(this, "No horses added. Please add horses first.");
    } else {
        // Dispose the current dialog and open the new frame
        this.dispose();      
        StartRaceGUI StartRaceGUIWindow = new StartRaceGUI(); 
        StartRaceGUIWindow.setVisible(true);
        
    }
}

      

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomiseHorse(null));
    }
}
