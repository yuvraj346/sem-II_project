import javax.swing.*;
import java.awt.*;

public class LoginPageSwing {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPageSwing::createAndShowGUI);
    }

    public static void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("Student Companion - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setResizable(false);

        // ================= HEADING PANEL =================
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(new Color(0, 0, 139)); // Dark blue
        JLabel headingLabel = new JLabel("Login Page");
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headingPanel.add(headingLabel);

        // ================= FORM PANEL =================
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80)); // Padding

        // Username field
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        userField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Limit height

        // Password field
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Error label
        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 13));

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(173, 216, 230)); // Light blue
        loginButton.setForeground(new Color(0, 0, 90));
        loginButton.setFocusPainted(false);

        // Action when login button is clicked
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if ((username.equals("yuvraj") && password.equals("1234")) ||
                    (username.equals("anjali") && password.equals("abcd"))) {

                // Open homepage (replace with actual home UI method)
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                frame.dispose(); // Close login window
                HomePageSwing.show(); // Call your Swing homepage here
            } else {
                errorLabel.setText("Invalid username or password");
            }
        });

        // Add components to the form panel
        formPanel.add(userLabel);
        formPanel.add(userField);
        formPanel.add(Box.createVerticalStrut(10)); // Space between fields
        formPanel.add(passLabel);
        formPanel.add(passField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(loginButton);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(errorLabel);

        // ================= MAIN PANEL =================
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headingPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Add main panel to frame and show
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
}
