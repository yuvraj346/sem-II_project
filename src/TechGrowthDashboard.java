import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.swing.Timer;

/**
 * Tech-Growth Dashboard v2
 * ─ Bar chart (unchanged)
 * ─ Soft-skills section
 * ─ AI mentor chatbot
 * ─ Mentor contact cards
 */
public class TechGrowthDashboard extends JPanel {

    /* ======= DATA CLASS ======= */
    private static class Entry {
        final String field;
        final int percent;
        final String tools;

        Entry(String field, int percent, String tools) {
            this.field = field;
            this.percent = percent;
            this.tools = tools;
        }
    }

    /* ======= COLORS ======= */
    private static final Color BG = new Color(25, 25, 40);
    private static final Color BAR_BG = new Color(50, 50, 70);
    private static final Color BAR_FILL = new Color(30, 144, 255);
    private static final Color TEXT = new Color(220, 220, 220);
    private static final Color ACCENT = new Color(0, 180, 216);

    /* ======= FIELDS ======= */
    private final java.util.List<Entry> data = new ArrayList<>();
    private int frame = 0;
    private static final int FRAMES = 30;
    private Timer animator;

    /* ======= DIMENSIONS ======= */
    private static final int BAR_H = 28;
    private static final int GAP = 14;
    private static final int LEFT_W = 260;
    private static final int RIGHT_W = 60;

    public TechGrowthDashboard() {
        loadData();
        data.sort((a, b) -> b.percent - a.percent);
        setBackground(BG);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        ToolTipManager.sharedInstance().setInitialDelay(0);

        animator = new Timer(20, e -> {
            frame++;
            repaint();
            if (frame >= FRAMES) animator.stop();
        });
        animator.start();
    }

    private void loadData() {
        data.add(new Entry("AI & ML", 36, "Python, TensorFlow, PyTorch, Keras, MLOps"));
        data.add(new Entry("Data Science", 36, "Python, R, SQL, Spark, Tableau"));
        data.add(new Entry("Cloud Computing", 25, "AWS, Azure, GCP, Docker, K8s, Terraform"));
        data.add(new Entry("Cybersecurity", 25, "SIEM, Firewalls, IAM, 3.5 M jobs"));
        data.add(new Entry("Software & DevOps", 22, "React, Node, Java, Docker, Jenkins"));
        data.add(new Entry("Blockchain", 15, "Ethereum, Solidity, Hyperledger"));
        data.add(new Entry("IoT", 15, "MQTT, AWS IoT, Arduino, Raspberry Pi"));
        data.add(new Entry("AR/VR", 15, "Unity, Unreal, ARKit, ARCore"));
        data.add(new Entry("Quantum Computing", 10, "Qiskit, Cirq, Q#, Azure Quantum"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int usableW = w - LEFT_W - RIGHT_W;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 18);
        Font toolsFont = new Font("Segoe UI", Font.PLAIN, 13);
        g2.setFont(labelFont);
        FontMetrics fm = g2.getFontMetrics();

        int y = 0;
        for (Entry e : data) {
            g2.setColor(TEXT);
            g2.drawString(e.field, 0, y + BAR_H / 2 + fm.getAscent() / 2 - 2);

            g2.setColor(BAR_BG);
            g2.fillRect(LEFT_W, y, usableW, BAR_H);

            int len = (int) (usableW * e.percent / 100.0 *
                    (1 - Math.pow(1 - (double) frame / FRAMES, 3)));
            g2.setColor(BAR_FILL);
            g2.fillRect(LEFT_W, y, len, BAR_H);

            g2.setColor(TEXT);
            g2.drawString(e.percent + "%", w - RIGHT_W + 10, y + BAR_H / 2 + fm.getAscent() / 2 - 2);

            g2.setFont(toolsFont);
            g2.drawString(e.tools, LEFT_W, y + BAR_H + 15);
            g2.setFont(labelFont);

            y += BAR_H + GAP + 20;
        }
        g2.dispose();
    }

    @Override
    public String getToolTipText(MouseEvent e) {
        int idx = e.getY() / (BAR_H + GAP + 20);
        if (idx >= 0 && idx < data.size()) {
            Entry en = data.get(idx);
            return "<html><b>" + en.field + "</b><br>" +
                    en.percent + "% growth<br>" +
                    en.tools + "</html>";
        }
        return null;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(920, 30 + data.size() * (BAR_H + GAP + 20) + 30);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Tech Growth Dashboard 2025");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel root = new JPanel();
            root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
            root.setBackground(BG);
            root.setBorder(new EmptyBorder(20, 20, 20, 20));

            TechGrowthDashboard chartPanel = new TechGrowthDashboard();
            chartPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            chartPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                    chartPanel.getPreferredSize().height));
            root.add(chartPanel);
            root.add(Box.createVerticalStrut(20));

            JScrollPane masterScroll = new JScrollPane(root,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            masterScroll.getVerticalScrollBar().setUnitIncrement(16);
            masterScroll.setBorder(null);

            f.setContentPane(masterScroll);
            f.setSize(1000, 800);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
