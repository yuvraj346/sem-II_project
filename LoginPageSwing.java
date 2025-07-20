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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        // ================= HEADING PANEL WITH GRADIENT =================
        JPanel headingPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, new Color(0, 0, 139), getWidth(), 0, new Color(70, 130, 180)));
                g2.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
                g2.dispose();
            }
        };
        headingPanel.setPreferredSize(new Dimension(0, 80));
        headingPanel.setOpaque(false);

        // Logo placeholder in upper right corner
        ImageIcon logoIcon = new ImageIcon("college_logo.png"); // Replace with your logo path
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setPreferredSize(new Dimension(60, 60));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        // Heading label
        JLabel headingLabel = new JLabel("Student Companion");
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 0));

        headingPanel.add(headingLabel, BorderLayout.WEST);
        headingPanel.add(logoLabel, BorderLayout.EAST);

        // ================= FORM PANEL WITH GRADIENT BACKGROUND =================
        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, new Color(240, 248, 255), getWidth(), getHeight(), new Color(230, 240, 250)));
                g2.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
                g2.dispose();
            }
        };
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        // Login subtitle
        JLabel loginSubtitle = new JLabel("Welcome Back!");
        loginSubtitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginSubtitle.setForeground(new Color(0, 0, 139));
        loginSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginSubtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Username field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(new Color(0, 0, 139));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField userField = new JTextField();
        userField.setMaximumSize(new Dimension(300, 35));
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Password field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passLabel.setForeground(new Color(0, 0, 139));
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(300, 35));
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Error label
        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(new Color(220, 53, 69));
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login button with gradient
        JButton loginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setPaint(new GradientPaint(0, 0, new Color(0, 0, 100), getWidth(), getHeight(), new Color(70, 130, 180)));
                } else if (getModel().isRollover()) {
                    g2.setPaint(new GradientPaint(0, 0, new Color(0, 0, 120), getWidth(), getHeight(), new Color(100, 149, 237)));
                } else {
                    g2.setPaint(new GradientPaint(0, 0, new Color(0, 0, 139), getWidth(), getHeight(), new Color(70, 130, 180)));
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        loginButton.setContentAreaFilled(false);
        loginButton.setOpaque(false);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(200, 40));
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Action when login button is clicked
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if ((username.equals("yuvraj") && password.equals("1234")) ||
                    (username.equals("anjali") && password.equals("abcd"))) {

                JOptionPane.showMessageDialog(frame, "Login Successful!");
                frame.dispose();
                HomePageSwing.show();
            } else {
                errorLabel.setText("Invalid username or password");
            }
        });

        // Add components to the form panel
        formPanel.add(loginSubtitle);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(userLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(userField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(passLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(passField);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(loginButton);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(errorLabel);

        // ================= MAIN PANEL =================
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headingPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
}
