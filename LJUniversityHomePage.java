package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import ui.TaskManagerUI;

public final class HomePageSwing {

    // Enhanced color palette - Cool modern theme
    private static final Color BLUE_DARK = new Color(25, 118, 210);
    private static final Color BLUE_LIGHT = new Color(66, 165, 245);
    private static final Color BLUE_ACCENT = new Color(3, 169, 244);
    private static final Color WHITE = Color.WHITE;
    private static final Color BG_LIGHT = new Color(248, 250, 252);
    private static final Color BG_DARKER = new Color(240, 242, 245);
    private static final Color SHADOW = new Color(0, 0, 0, 30);

    public static void show() {
        // Create main frame
        JFrame frame = new JFrame("LJ University - Student Companion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        // ================= HEADER =================
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, BLUE_DARK, getWidth(), 0, BLUE_LIGHT));
                g2.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(0, 80));
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(15, 30, 15, 30));

        // University title - Simple as requested
        JLabel title = new JLabel("LJ University");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(WHITE);

        // Logo placeholder
        ImageIcon logoIcon = new ImageIcon("C:/Users/YUVRAJ/IdeaProjects/lj companion/src/img.png"); // Replace with your logo path
        Image img = logoIcon.getImage();
        Image newImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(newImg));
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(title, BorderLayout.WEST);
        header.add(logo, BorderLayout.EAST);
        frame.add(header, BorderLayout.NORTH);

        // ================= MAIN CONTENT WITH SCROLL =================
        JPanel mainContent = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, BG_LIGHT, getWidth(), getHeight(), BG_DARKER));
                g2.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
                g2.dispose();
            }
        };
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setOpaque(false);

        // ================= GREETING BANNER =================
        JPanel greetingPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow effect
                g2.setColor(SHADOW);
                g2.fillRoundRect(5, 5, getWidth()-10, getHeight()-10, 25, 25);

                // Main gradient
                g2.setPaint(new GradientPaint(0, 0, new Color(25, 118, 210, 220), getWidth(), getHeight(), new Color(66, 165, 245, 220)));
                g2.fillRoundRect(0, 0, getWidth()-5, getHeight()-5, 25, 25);

                super.paintComponent(g);
                g2.dispose();
            }
        };
        greetingPanel.setOpaque(false);
        greetingPanel.setBorder(new EmptyBorder(40, 60, 40, 60));
        greetingPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 200));

        JLabel greeting = new JLabel("<html><center><h1>🚀 Welcome to LJ University!</h1><br>" +
                "<h2>Your academic journey starts here</h2><br>" +
                "Explore our comprehensive student services and make the most of your university experience</center></html>");
        greeting.setFont(new Font("Segoe UI", Font.BOLD, 24));
        greeting.setForeground(WHITE);
        greeting.setHorizontalAlignment(SwingConstants.CENTER);
        greetingPanel.add(greeting, BorderLayout.CENTER);

        mainContent.add(Box.createVerticalStrut(30));
        mainContent.add(greetingPanel);
        mainContent.add(Box.createVerticalStrut(30));

        // ================= SHOUTOUT SECTION =================
        JPanel shoutoutPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow
                g2.setColor(SHADOW);
                g2.fillRoundRect(3, 3, getWidth()-6, getHeight()-6, 20, 20);

                // Main panel
                g2.setColor(WHITE);
                g2.fillRoundRect(0, 0, getWidth()-3, getHeight()-3, 20, 20);

                super.paintComponent(g);
                g2.dispose();
            }
        };
        shoutoutPanel.setOpaque(false);
        shoutoutPanel.setBorder(new EmptyBorder(25, 35, 25, 35));
        shoutoutPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 140));

        JLabel shoutoutTitle = new JLabel("🏆 Recent Winners & Achievements");
        shoutoutTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        shoutoutTitle.setForeground(BLUE_DARK);

        JLabel shoutoutContent = new JLabel("🎮 Gaming Champions: Team Phoenix | 📚 Academic Excellence: Sarah Johnson | 🏅 Sports Champions: Basketball Team Alpha");
        shoutoutContent.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        shoutoutContent.setForeground(new Color(100, 100, 100));

        shoutoutPanel.add(shoutoutTitle, BorderLayout.NORTH);
        shoutoutPanel.add(shoutoutContent, BorderLayout.CENTER);

        mainContent.add(shoutoutPanel);
        mainContent.add(Box.createVerticalStrut(40));

        // ================= OPTIONS GRID =================
        JPanel optionsPanel = new JPanel(new GridLayout(2, 3, 35, 35));
        optionsPanel.setOpaque(false);
        optionsPanel.setBorder(new EmptyBorder(20, 120, 40, 120));

        String[] options = {
                "Career Guidance", "Gaming & Leaderboard", "Task Manager",
                "Complaint Box", "Student Services", "Academic Resources"
        };

        for (String option : options) {
            optionsPanel.add(createOptionButton(option, frame));
        }

        mainContent.add(optionsPanel);
        mainContent.add(Box.createVerticalGlue());

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setBackground(BG_DARKER);
        scrollPane.getVerticalScrollBar().setForeground(BLUE_DARK);
        frame.add(scrollPane, BorderLayout.CENTER);

        // ================= FOOTER =================
        JPanel footer = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, BLUE_DARK, getWidth(), 0, BLUE_LIGHT));
                g2.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
                g2.dispose();
            }
        };
        footer.setPreferredSize(new Dimension(0, 60));
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(15, 30, 15, 30));

        JLabel contact = new JLabel("📧 Contact: support@ljuniversity.in | 📞 Phone: +91-XXXXXXXXXX");
        contact.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contact.setForeground(WHITE);

        JLabel copyright = new JLabel("© 2024 LJ University. All rights reserved.");
        copyright.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        copyright.setForeground(WHITE);

        footer.add(contact, BorderLayout.WEST);
        footer.add(copyright, BorderLayout.EAST);
        frame.add(footer, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Create enhanced interactive option buttons
    private static JButton createOptionButton(String text, JFrame parentFrame) {
        JButton button = new JButton("<html><center><h2>" + text + "</h2><br>✨ Click to explore</center></html>") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow effect
                g2.setColor(SHADOW);
                g2.fillRoundRect(4, 4, getWidth()-8, getHeight()-8, 20, 20);

                if (getModel().isPressed()) {
                    g2.setPaint(new GradientPaint(0, 0, BLUE_DARK, getWidth(), getHeight(), BLUE_LIGHT));
                } else if (getModel().isRollover()) {
                    g2.setPaint(new GradientPaint(0, 0, BLUE_ACCENT, getWidth(), getHeight(), BLUE_LIGHT));
                } else {
                    g2.setPaint(new GradientPaint(0, 0, WHITE, getWidth(), getHeight(), BG_LIGHT));
                }

                g2.fillRoundRect(0, 0, getWidth()-4, getHeight()-4, 20, 20);

                // Border
                g2.setColor(BLUE_DARK);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-6, getHeight()-6, 20, 20);

                super.paintComponent(g);
                g2.dispose();
            }
        };

        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setForeground(BLUE_DARK);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(220, 130));

        button.addActionListener(e -> {
            switch (text) {
                case "Task Manager":
                    parentFrame.setVisible(false);
                    TaskManagerUI.openTaskManager();
                    break;
                case "Career Guidance":
                    JOptionPane.showMessageDialog(parentFrame, "🎯 Career Guidance module coming soon!", "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Gaming & Leaderboard":
                    JOptionPane.showMessageDialog(parentFrame, "🎮 Gaming & Leaderboard module coming soon!", "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Complaint Box":
                    JOptionPane.showMessageDialog(parentFrame, "📝 Complaint Box module coming soon!", "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Student Services":
                    JOptionPane.showMessageDialog(parentFrame, "🛠️ Student Services module coming soon!", "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Academic Resources":
                    JOptionPane.showMessageDialog(parentFrame, "📚 Academic Resources module coming soon!", "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomePageSwing::show);
    }
}
