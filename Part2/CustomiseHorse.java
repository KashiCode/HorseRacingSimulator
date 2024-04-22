package Part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomiseHorse extends JFrame {
    public CustomiseHorse() {
        setTitle("Customise Horse");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window on close
        getContentPane().setBackground(Color.WHITE);

        // Main layout with vertical box layout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add a title label for the window
        JLabel titleLabel = new JLabel("Customise Your Horses", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label
        add(titleLabel);

        // Add a panel with titles for the fields
        add(createTitlePanel());

        // Create form fields for 6 horses
        for (int i = 0; i < 6; i++) {
            add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
            add(createHorseCustomisationPanel(i + 1));
        }

        // Add control buttons at the bottom
        add(createControlPanel());

        pack(); // Pack the components tightly together
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(new JLabel("Name"));
        titlePanel.add(Box.createHorizontalStrut(15)); // Spacer
        titlePanel.add(new JLabel("Symbol"));
        titlePanel.add(Box.createHorizontalStrut(15)); // Spacer
        titlePanel.add(new JLabel("Confidence"));
        titlePanel.add(Box.createHorizontalStrut(15)); // Spacer
        titlePanel.add(new JLabel("Breed"));
        titlePanel.add(Box.createHorizontalStrut(15)); // Spacer
        titlePanel.add(new JLabel("Color"));
        titlePanel.add(Box.createHorizontalStrut(15)); // Spacer
        titlePanel.add(new JLabel("Accessory"));

        return titlePanel;
    }

    private JPanel createHorseCustomisationPanel(int horseNumber) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(new JLabel("Horse " + horseNumber + ": "));
        panel.add(Box.createHorizontalStrut(5)); // Spacer

        JTextField nameField = new JTextField(10);
        panel.add(nameField);
        panel.add(Box.createHorizontalStrut(5)); // Spacer

        JTextField symbolField = new JTextField(2);
        panel.add(symbolField);
        panel.add(Box.createHorizontalStrut(5)); // Spacer

        JTextField confidenceField = new JTextField(5);
        panel.add(confidenceField);
        panel.add(Box.createHorizontalStrut(5)); // Spacer

        String[] breeds = {"Breed 1", "Breed 2", "Breed 3", "Breed 4"};
        JComboBox<String> breedComboBox = new JComboBox<>(breeds);
        panel.add(breedComboBox);
        panel.add(Box.createHorizontalStrut(5)); // Spacer

        String[] colors = {"Color 1", "Color 2", "Color 3", "Color 4"};
        JComboBox<String> colorComboBox = new JComboBox<>(colors);
        panel.add(colorComboBox);
        panel.add(Box.createHorizontalStrut(5)); // Spacer

        String[] accessories = {"Accessory 1", "Accessory 2", "Accessory 3", "Accessory 4"};
        JComboBox<String> accessoryComboBox = new JComboBox<>(accessories);
        panel.add(accessoryComboBox);

        return panel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton continueButton = new JButton("CONTINUE");
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action for the CONTINUE button
                JOptionPane.showMessageDialog(CustomiseHorse.this, "Continue to the next phase.");
            }
        });

        JButton exitButton = new JButton("EXIT");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action for the EXIT button
                System.exit(0);
            }
        });

        controlPanel.add(continueButton);
        controlPanel.add(exitButton);

        return controlPanel;
    }
}
