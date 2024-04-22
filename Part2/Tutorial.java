package Part2;

import javax.swing.*;
import java.awt.*;

public class Tutorial extends JFrame {
    public Tutorial() {
        setTitle("Tutorial");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JTextArea tutorialText = new JTextArea();


        tutorialText.setText("Step 1: Do this\nStep 2: Do that\n...");
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