import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class TechGrowthChart extends JPanel {

    /* ======= DATA ======= */
    private static class Entry {
        final String field;
        final int    percent;
        final String tools;

        Entry(String f, int p, String t) { field = f; percent = p; tools = t; }
    }

    /* Dark-theme colours */
    private static final Color BG       = new Color(25, 25, 40);
    private static final Color BAR_BG   = new Color(50, 50, 70);
    private static final Color BAR_FILL = new Color(30, 144, 255);
    private static final Color TEXT     = new Color(220, 220, 220);

    private final List<Entry> data = new ArrayList<>();
    private final int[] barLength;            // animated width
    private int frame = 0;
    private static final int FRAMES = 30;
    private Timer animator = null; // <-- fully-qualified

    /* ======= CONSTRUCTOR ======= */
    public TechGrowthChart() {
        loadData();
        data.sort((a, b) -> b.percent - a.percent);   // tallest first
        barLength = new int[data.size()];

        setBackground(BG);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        /* simple animation timer */
        animator = new Timer(20, e -> {   // <-- fully-qualified
            frame++;
            repaint();
            if (frame >= FRAMES) animator.stop();
        });
        animator.start();

        /* tool-tips */
        ToolTipManager.sharedInstance().setInitialDelay(0);
    }

    /* ======= DATA ======= */
    private void loadData() {
        data.add(new Entry("AI & ML",                36, "Python, TensorFlow, PyTorch, Keras, MLOps"));
        data.add(new Entry("Data Science",           36, "Python, R, SQL, Spark, Tableau"));
        data.add(new Entry("Cloud Computing",        25, "AWS, Azure, GCP, Docker, K8s, Terraform"));
        data.add(new Entry("Cybersecurity",          25, "SIEM, Firewalls, IAM, 3.5 M jobs"));
        data.add(new Entry("Software & DevOps",      22, "React, Node, Java, Docker, Jenkins"));
        data.add(new Entry("Blockchain",             15, "Ethereum, Solidity, Hyperledger"));
        data.add(new Entry("IoT",                    15, "MQTT, AWS IoT, Arduino, Raspberry Pi"));
        data.add(new Entry("AR/VR",                  15, "Unity, Unreal, ARKit, ARCore"));
        data.add(new Entry("Quantum Computing",      10, "Qiskit, Cirq, Q#, Azure Quantum"));
    }

    /* ======= PAINT ======= */
    private static final int BAR_H   = 28;
    private static final int GAP     = 14;
    private static final int LEFT_W  = 260;
    private static final int RIGHT_W = 60;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int usableW = w - LEFT_W - RIGHT_W;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 20);
        Font toolsFont = new Font("Segoe UI", Font.PLAIN, 11);
        g2.setFont(labelFont);
        FontMetrics fm = g2.getFontMetrics();

        int y = 0;
        for (int i = 0; i < data.size(); i++) {
            Entry e = data.get(i);

            /* field label */
            g2.setColor(TEXT);
            g2.drawString(e.field, 0, y + BAR_H / 2 + fm.getAscent() / 2 - 2);

            /* track */
            g2.setColor(BAR_BG);
            g2.fillRect(LEFT_W, y, usableW, BAR_H);

            /* animated fill */
            int len = (int) (usableW * e.percent / 100.0 *
                    (1 - Math.pow(1 - (double) frame / FRAMES, 3)));
            g2.setColor(BAR_FILL);
            g2.fillRect(LEFT_W, y, len, BAR_H);

            /* percent label */
            String pct = e.percent + "%";
            g2.drawString(pct, w - RIGHT_W + 10,
                    y + BAR_H / 2 + fm.getAscent() / 2 - 2);

            /* tools */
            g2.setFont(toolsFont);
            g2.setColor(TEXT);
            g2.drawString(e.tools, LEFT_W, y + BAR_H + 15);

            y += BAR_H + GAP + 20;
        }
        g2.dispose();
    }

    /* ======= TOOL-TIP ======= */
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

    /* ======= SIZE ======= */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(920,
                30 + data.size() * (BAR_H + GAP + 20) + 30);
    }

    /* ======= MAIN ======= */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Tech Fields – Market Demand 2025");
            TechGrowthChart chart = new TechGrowthChart();
            f.setContentPane(new JScrollPane(chart) {{
                setBorder(null);
                getVerticalScrollBar().setUnitIncrement(16);
            }});
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}