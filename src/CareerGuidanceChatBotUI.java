import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CareerGuidanceChatBotUI {

    Chatbot.GeminiClient gemini = new Chatbot.GeminiClient(); // Connect your AI

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CareerGuidanceChatBotUI().createUI());
    }

    public void createUI() {
        JFrame frame = new JFrame("Career Guidance - AI Chatbot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // ---------------- HEADER PANEL ----------------
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(15, 30, 70)); // Dark blue
        headerPanel.setPreferredSize(new Dimension(800, 60));
        JLabel heading = new JLabel("Career Guidance");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        headerPanel.add(heading);

        // ---------------- BODY PANEL ----------------
        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBackground(Color.WHITE);

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // Input field panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputField.setPreferredSize(new Dimension(100, 40));

        JButton sendButton = new JButton("Send 💬");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sendButton.setBackground(new Color(15, 30, 70));
        sendButton.setForeground(Color.WHITE);

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Warning label
        JLabel warningLabel = new JLabel("⚠️ For freshers/new students only. Seniors can explore freely!");
        warningLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        warningLabel.setForeground(new Color(255, 100, 50));
        warningLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add to body panel
        bodyPanel.add(warningLabel, BorderLayout.NORTH);
        bodyPanel.add(scrollPane, BorderLayout.CENTER);
        bodyPanel.add(inputPanel, BorderLayout.SOUTH);

        // Action
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userText = inputField.getText().trim();
                if (!userText.isEmpty()) {
                    chatArea.append("You: " + userText + "\n");
                    inputField.setText("");

                    // Gemini Thinking (fake loading)
                    chatArea.append("Bot: (typing...)\n");

                    // Run AI on separate thread
                    new Thread(() -> {
                        String reply = gemini.sendMessage(userText);

                        SwingUtilities.invokeLater(() -> {
                            // Remove "(typing...)" line and print actual reply
                            chatArea.setText(chatArea.getText().replace("Bot: (typing...)\n", ""));
                            chatArea.append("Bot: " + reply + "\n\n");
                        });
                    }).start();
                }
            }
        });

        // Assemble the frame
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(bodyPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
