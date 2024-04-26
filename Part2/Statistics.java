package Part2;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * The Class Statistics creates a text area that displays both the Horse Attributes and the previous Race results. 
 * 
 * @author Maks Ostrynski
 * @version 1  26/04/2024
 */
public class Statistics extends JFrame {
    public Statistics() {
        setTitle("Horse Statistics");
        setSize(600, 400); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(233, 233, 233)); 

        JTextArea statsTextArea = new JTextArea();
        statsTextArea.setEditable(false);
        statsTextArea.setFont(new Font("Consolas", Font.PLAIN, 12)); 

        loadHorseAttributes(statsTextArea);
        loadRaceResults(statsTextArea);

        JScrollPane scrollPane = new JScrollPane(statsTextArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        add(scrollPane);

        setVisible(true);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    //Loads the Horse Attributes from the horseAttributes File. 
    private void loadHorseAttributes(JTextArea textArea) {

        String filePath = System.getProperty("user.dir") + File.separator + "horseAttribute.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            textArea.append("Horse Attributes:\n");
            textArea.append("Name | Symbol | Confidence | Color | Breed | Accessories\n");
            textArea.append("-----------------------------------------------------------------\n");
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                textArea.append(String.format("%s | %s | %.2f | %s | %s | %s\n",
                        details[0], details[1], Double.parseDouble(details[2]), details[3], details[4], details[5]));
            }
            textArea.append("\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to read horse attributes.");
            e.printStackTrace();
        }
    }

    //Loads the Horse Race Results from the raceResults File. 
    private void loadRaceResults(JTextArea textArea) {
        String filePath = System.getProperty("user.dir") + File.separator + "raceResults.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            textArea.append("Race Results:\n");
            textArea.append("Race | Name | Total Win | Total Fall | Confidence\n");
            textArea.append("-----------------------------------------------------------------\n");
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to read race results.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Statistics::new);
    }
}
