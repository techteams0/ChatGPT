import javax.swing.*;
import java.awt.*;
import ai.openai.*;
import java.util.*;

public class OpenAITechTeams {
    public static void main(String[] args) {
        OpenAI.apiKey = "Enter-your-api-key";

        String startSequence = "\nAI:";
        String restartSequence = "\nHuman: ";

        JFrame frame = new JFrame("OpenAI TechTeams");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // User input
        JLabel userInputLabel = new JLabel("Enter your question:");
        userInputLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        userInputLabel.setPreferredSize(new Dimension(500, 30));
        JTextField userInput = new JTextField(60);
        userInput.setFont(new Font("NUnit Sans", Font.PLAIN, 12));
        userInput.setBackground(Color.YELLOW);

        // Generate button
        JButton generateButton = new JButton("Generate response");

        // Response output
        JLabel responseOutputLabel = new JLabel("AI's response:");
        responseOutputLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        responseOutputLabel.setPreferredSize(new Dimension(1000, 30));
        JTextArea responseOutput = new JTextArea();
        responseOutput.setEditable(false);
        responseOutput.setLineWrap(true);
        responseOutput.setWrapStyleWord(true);
        responseOutput.setBackground(Color.GRAY);
        responseOutput.setForeground(Color.WHITE);
        responseOutput.setFont(new Font("Poppins", Font.BOLD, 10));

        // Add components to frame
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(userInputLabel);
        panel.add(userInput);
        panel.add(generateButton);
        panel.add(responseOutputLabel);
        JScrollPane scrollPane = new JScrollPane(responseOutput);
        scrollPane.setPreferredSize(new Dimension(1000, 500));
        panel.add(scrollPane);
        frame.getContentPane().add(panel);

        // Generate response when button is clicked
        generateButton.addActionListener(e -> {
            String ask = userInput.getText();
            if (ask.equals("break")) {
                responseOutput.append("thank you: ");
            } else {
                Completion response = Completion.create(
                    Model.Davinci,
                    ask,
                    new CompletionParameters.Builder()
                        .setTemperature(0.9)
                        .setMaxTokens(150)
                        .setTopP(1)
                        .setFrequencyPenalty(0)
                        .setPresencePenalty(0.6)
                        .addStop(" Human:")
                        .addStop(" AI:")
                        .build()
                );
                String responseText = response.getChoices().get(0).getText();
                responseOutput.append(startSequence + responseText + restartSequence);
            }
            responseOutput.setCaretPosition(responseOutput.getDocument().getLength());
        });

        frame.pack();
        frame.setVisible(true);
    }
}
