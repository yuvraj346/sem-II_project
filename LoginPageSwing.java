import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPageSwing extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginPageSwing() {
        setTitle("Student Companion - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Main panel with background image
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bgImage = new ImageIcon("C:\\Users\\YUVRAJ\\IdeaProjects\\example\\src\\bg4.jpg");
                g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        setContentPane(mainPanel);
        mainPanel.setLayout(new GridBagLayout());

        // Header panel for the logo and university name
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        // University Name Label
        JLabel universityLabel = new JLabel("LJ University");
        universityLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        universityLabel.setForeground(new Color(0, 102, 204)); // Changed to dark blue
        universityLabel.setBorder(new EmptyBorder(10, 20, 10, 10));
        headerPanel.add(universityLabel, BorderLayout.WEST);

        // Logo
        ImageIcon originalLogoIcon = new ImageIcon("C:\\Users\\YUVRAJ\\IdeaProjects\\example\\src\\img.png"); // Replace with your logo path
        Image originalLogoImage = originalLogoIcon.getImage();
        Image scaledLogoImage = originalLogoImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH); // Scale to 60x60
        ImageIcon scaledLogoIcon = new ImageIcon(scaledLogoImage);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        logoLabel.setBorder(new EmptyBorder(10, 10, 10, 20));
        headerPanel.add(logoLabel, BorderLayout.EAST);

        // Add header panel to the top
        GridBagConstraints headerGbc = new GridBagConstraints();
        headerGbc.gridx = 0;
        headerGbc.gridy = 0;
        headerGbc.weightx = 1.0;
        headerGbc.anchor = GridBagConstraints.NORTH;
        headerGbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(headerPanel, headerGbc);

        // Create a semi-transparent panel for the login form
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(new Color(255, 255, 255, 200)); // White with transparency
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBorder(new EmptyBorder(40, 40, 40, 40)); // Only EmptyBorder for padding
        loginPanel.setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("Student Companion");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(0, 102, 204));
        loginPanel.add(titleLabel, gbc);

        // Subtitle
        gbc.gridy++;
        JLabel subtitleLabel = new JLabel("Welcome Back!");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(Color.DARK_GRAY);
        loginPanel.add(subtitleLabel, gbc);

        // Username
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        usernameLabel.setForeground(new Color(0, 102, 204));
        loginPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 102, 204)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        loginPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passwordLabel.setForeground(new Color(0, 102, 204));
        loginPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 102, 204)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        loginPanel.add(passwordField, gbc);

        // Login Button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(new EmptyBorder(10, 40, 10, 40));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Button hover effect
        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                loginButton.setBackground(new Color(0, 81, 163));
            }

            public void mouseExited(MouseEvent evt) {
                loginButton.setBackground(new Color(0, 102, 204));
            }
        });

        loginPanel.add(loginButton, gbc);

        // Error Label
        gbc.gridy++;
        JLabel errorLabel = new JLabel("");
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setForeground(Color.RED);
        loginPanel.add(errorLabel, gbc);

        // Action Listener for Login Button
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if ((username.equals("yuvraj") && password.equals("1234")) ||
                    (username.equals("anjali") && password.equals("abcd"))) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose();
                HomePageSwing.show();
            } else {
                errorLabel.setText("Invalid username or password. Please try again.");
            }
        });

        // Add login panel to the center
        GridBagConstraints loginGbc = new GridBagConstraints();
        loginGbc.gridx = 0;
        loginGbc.gridy = 1;
        loginGbc.weighty = 1.0; // Push the login panel to the center vertically
        loginGbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginPanel, loginGbc);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPageSwing::new);
    }
}
