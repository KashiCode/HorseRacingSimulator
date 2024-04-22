package Part2;

import javax.swing.*;
import java.awt.*;

public class CustomiseHorse extends JFrame {
    public CustomiseHorse() {
        setTitle("Customise Horse");
        setLayout(new GridLayout(7, 1, 10, 10)); // Grid layout for 6 horses plus a title row
        getContentPane().setBackground(Color.WHITE);

        // Add a title label
        JLabel titleLabel = new JLabel("Customise Your Horses", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel);

        // Create form fields for 6 horses
        for (int i = 0; i < 6; i++) {
            add(createHorseCustomisationPanel(i + 1));
        }

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createHorseCustomisationPanel(int horseNumber) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Horse " + horseNumber + ": "));
        
        JTextField nameField = new JTextField(10);
        panel.add(nameField);
        
        JTextField symbolField = new JTextField(1);
        panel.add(symbolField);

        JSlider confidenceSlider = new JSlider(0, 100);
        confidenceSlider.setMajorTickSpacing(25);
        confidenceSlider.setMinorTickSpacing(5);
        confidenceSlider.setPaintTicks(true);
        confidenceSlider.setPaintLabels(true);
        panel.add(confidenceSlider);

        String[] breeds = {"Breed 1", "Breed 2", "Breed 3", "Breed 4"};
        JComboBox<String> breedComboBox = new JComboBox<>(breeds);
        panel.add(breedComboBox);

        String[] colors = {"Color 1", "Color 2", "Color 3", "Color 4"};
        JComboBox<String> colorComboBox = new JComboBox<>(colors);
        panel.add(colorComboBox);

        String[] accessories = {"Accessory 1", "Accessory 2", "Accessory 3", "Accessory 4"};
        JComboBox<String> accessoryComboBox = new JComboBox<>(accessories);
        panel.add(accessoryComboBox);

        return panel;
    }
}
