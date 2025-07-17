package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

 final class TaskManagerPanel extends JPanel {

    /* ---------- COLOUR PALETTE (identical to HomePageSwing) ---------- */
    private static final Color BG_DARK   = new Color(18, 18, 26);
    private static final Color BG_CARD   = new Color(30, 31, 38);
    private static final Color ACCENT    = new Color(0, 188, 212);
    private static final Color TEXT      = new Color(230, 230, 230);
    private static final Color TEXT_MUTE = new Color(150, 150, 150);
    private static final Font  FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font  FONT_BODY  = new Font("Segoe UI", Font.PLAIN, 14);

    /* ---------- DB ---------- */
    private static final String URL  = "jdbc:mysql://localhost:3306/taskmanager";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /* ---------- STATE ---------- */
    private String currentEnrollment;
    private Connection con;
    private final DefaultListModel<Task> taskModel = new DefaultListModel<>();
    private final JList<Task> taskList = new JList<>(taskModel);

    /* =========================================================
       PUBLIC ENTRY POINT
       ========================================================= */
    public static void open(JFrame parent) {
        JDialog dlg = new JDialog(parent, "Task Manager", true);
        dlg.setSize(920, 720);
        dlg.setLocationRelativeTo(parent);

        TaskManagerPanel panel = new TaskManagerPanel();
        dlg.setContentPane(panel);

        dlg.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                parent.setVisible(true);   // re-show home
            }
        });
        panel.setOnBack(() -> {
            dlg.dispose();
            parent.setVisible(true);
        });

        dlg.setVisible(true);
    }

    /* =========================================================
       CONSTRUCTOR
       ========================================================= */
    private Runnable onBack;

    public void setOnBack(Runnable r) { this.onBack = r; }

    private TaskManagerPanel() {
        super(new BorderLayout());
        setBackground(BG_DARK);

        /* ---------- HEADER ---------- */
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_CARD);
        header.setPreferredSize(new Dimension(0, 60));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("Task Manager");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT);

        JButton back = new JButton("← Back");
        styleButton(back, false);
        back.addActionListener(e -> { if (onBack != null) onBack.run(); });

        header.add(title, BorderLayout.WEST);
        header.add(back,  BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        /* ---------- MAIN AREA (CardLayout) ---------- */
        buildWelcomeCard();
        buildTaskCard();
        add(center, BorderLayout.CENTER);

        connectDB();
    }

    private final CardLayout cards = new CardLayout();
    private final JPanel     center = new JPanel(cards);

    /* =========================================================
       DATABASE
       ========================================================= */
    private void connectDB() {
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "DB connection error: " + ex.getMessage());
        }
    }

    /* =========================================================
       WELCOME CARD – enrollment input
       ========================================================= */
    private JTextField enrollField;
    private void buildWelcomeCard() {
        JPanel welcome = new JPanel(new GridBagLayout());
        welcome.setOpaque(false);

        enrollField = new JTextField(18);
        style(enrollField);

        JButton go = new JButton("Search / Sign-up");
        styleButton(go, true);
        go.addActionListener(e -> openEnrollment());

        welcome.add(label("Enrollment Number:"), gbc(0, 0));
        welcome.add(enrollField, gbc(1, 0));
        welcome.add(Box.createVerticalStrut(20), gbc(0, 1));
        welcome.add(go, gbc(1, 1));

        center.add(welcome, "welcome");
    }

    /* =========================================================
       TASK CARD – buttons TOP-LEFT, tasks CENTERED
       ========================================================= */
    private JPanel taskCard;
    private void buildTaskCard() {

        /* ---- BUTTON BAR ---- */
        JButton addBtn   = new JButton("➕ Add");
        JButton doneBtn  = new JButton("✓ Mark Done");
        JButton editBtn  = new JButton("✎ Edit");
        JButton delBtn   = new JButton("🗑 Delete");

        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        btnBar.setOpaque(false);
        btnBar.setBorder(new EmptyBorder(10, 20, 10, 20));
        for (JButton b : new JButton[]{addBtn, doneBtn, editBtn, delBtn}) {
            styleButton(b, true);
            btnBar.add(b);
        }
        addBtn.addActionListener(e  -> addTaskDialog());
        doneBtn.addActionListener(e -> markDone());
        editBtn.addActionListener(e -> editTaskDialog());
        delBtn.addActionListener(e  -> deleteTask());

        /* ---- TASK LIST ---- */
        taskList.setCellRenderer(new TaskRenderer());
        taskList.setOpaque(false);
        taskList.setBackground(BG_DARK);
        taskList.setFixedCellHeight(50);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        /* wrap list in a panel so it can be centered */
        JScrollPane scroll = new JScrollPane(taskList);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 40, 40, 40));

        JPanel listWrapper = new JPanel(new GridBagLayout());
        listWrapper.setOpaque(false);
        listWrapper.add(scroll, new GridBagConstraints());

        /* ---- assemble ---- */
        taskCard = new JPanel(new BorderLayout());
        taskCard.setOpaque(false);
        taskCard.add(btnBar, BorderLayout.NORTH);
        taskCard.add(listWrapper, BorderLayout.CENTER);

        center.add(taskCard, "tasks");
    }

    /* =========================================================
       NAVIGATION & CRUD  (identical logic, escaped `div`)
       ========================================================= */
    private void openEnrollment() {
        String en = enrollField.getText().trim();
        if (en.isEmpty()) return;

        String sql = "SELECT name, `div` FROM studentinfo WHERE enrollment_number=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, en);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                JTextField nameF = new JTextField();
                JTextField divF  = new JTextField();
                JPanel p = new JPanel(new GridLayout(0, 1, 5, 5));
                p.add(new JLabel("Name:"));
                p.add(nameF);
                p.add(new JLabel("Division:"));
                p.add(divF);
                int ok = JOptionPane.showConfirmDialog(this, p, "Sign-up", JOptionPane.OK_CANCEL_OPTION);
                if (ok == JOptionPane.OK_OPTION) {
                    try (PreparedStatement ins = con.prepareStatement(
                            "INSERT INTO studentinfo(enrollment_number,name,`div`) VALUES (?,?,?)")) {
                        ins.setString(1, en);
                        ins.setString(2, nameF.getText().trim());
                        ins.setString(3, divF.getText().trim());
                        ins.executeUpdate();
                    }
                } else return;
            }
            currentEnrollment = en;
            loadTasks();
            cards.show(center, "tasks");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadTasks() {
        taskModel.clear();
        String sql = "SELECT id, title, description, deadline, status FROM tasks WHERE enrollment_number=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, currentEnrollment);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                taskModel.addElement(new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("deadline").toLocalDate(),
                        rs.getString("status")
                ));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void addTaskDialog() {
        JTextField titleF = new JTextField();
        JTextArea  descA  = new JTextArea(4, 20);
        JFormattedTextField dateF = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        dateF.setColumns(12);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(new JLabel("Title:"));
        p.add(titleF);
        p.add(new JLabel("Description:"));
        p.add(new JScrollPane(descA));
        p.add(new JLabel("Deadline (yyyy-MM-dd):"));
        p.add(dateF);

        int ok = JOptionPane.showConfirmDialog(this, p, "Add Task", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            String sql = "INSERT INTO tasks(enrollment_number,title,description,deadline,status) VALUES (?,?,?,?,?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, currentEnrollment);
                ps.setString(2, titleF.getText().trim());
                ps.setString(3, descA.getText().trim());
                ps.setDate(4, Date.valueOf(dateF.getText()));
                ps.setString(5, "Incomplete");
                ps.executeUpdate();
                loadTasks();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    private void editTaskDialog() {
        Task t = taskList.getSelectedValue();
        if (t == null) return;

        JTextField titleF = new JTextField(t.title);
        JTextArea  descA  = new JTextArea(t.description, 4, 20);
        JFormattedTextField dateF = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        dateF.setValue(Date.valueOf(t.deadline));
        JComboBox<String> statusC = new JComboBox<>(new String[]{"Incomplete", "Completed"});
        statusC.setSelectedItem(t.status);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(new JLabel("Title:"));
        p.add(titleF);
        p.add(new JLabel("Description:"));
        p.add(new JScrollPane(descA));
        p.add(new JLabel("Deadline:"));
        p.add(dateF);
        p.add(new JLabel("Status:"));
        p.add(statusC);

        int ok = JOptionPane.showConfirmDialog(this, p, "Edit Task", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            String sql = "UPDATE tasks SET title=?, description=?, deadline=?, status=? WHERE id=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, titleF.getText().trim());
                ps.setString(2, descA.getText().trim());
                ps.setDate(3, Date.valueOf(dateF.getText()));
                ps.setString(4, (String) statusC.getSelectedItem());
                ps.setInt(5, t.id);
                ps.executeUpdate();
                loadTasks();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    private void markDone() {
        Task t = taskList.getSelectedValue();
        if (t == null) return;
        try (PreparedStatement ps = con.prepareStatement(
                "UPDATE tasks SET status='Completed' WHERE id=?")) {
            ps.setInt(1, t.id);
            ps.executeUpdate();
            loadTasks();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void deleteTask() {
        Task t = taskList.getSelectedValue();
        if (t == null) return;
        try (PreparedStatement ps = con.prepareStatement(
                "DELETE FROM tasks WHERE id=?")) {
            ps.setInt(1, t.id);
            ps.executeUpdate();
            loadTasks();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    /* =========================================================
       UTILS
       ========================================================= */
    private static JLabel label(String txt) {
        JLabel l = new JLabel(txt);
        l.setForeground(TEXT);
        l.setFont(FONT_BODY);
        return l;
    }

    private static void style(JTextField c) {
        c.setFont(FONT_BODY);
        c.setBackground(BG_CARD);
        c.setForeground(TEXT);
        c.setCaretColor(TEXT);
        c.setBorder(BorderFactory.createLineBorder(ACCENT));
    }

    private static void styleButton(AbstractButton b, boolean accent) {
        b.setFont(FONT_BODY.deriveFont(Font.BOLD));
        b.setForeground(TEXT);
        b.setBackground(accent ? ACCENT : BG_CARD);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(accent ? ACCENT : TEXT_MUTE, 2));
    }

    private static GridBagConstraints gbc(int x, int y) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = x; g.gridy = y; g.insets = new Insets(5, 5, 5, 5);
        return g;
    }

    /* =========================================================
       DATA CLASS
       ========================================================= */
    private static final class Task {
        final int id; final String title, description, status;
        final LocalDate deadline;
        Task(int id, String title, String description, LocalDate deadline, String status) {
            this.id = id; this.title = title; this.description = description;
            this.deadline = deadline; this.status = status;
        }
        @Override public String toString() { return title; }
    }

    /* =========================================================
       RENDERER
       ========================================================= */
    private static final class TaskRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Task t = (Task) value;
            l.setText(String.format("<html><b>%s</b> &nbsp;&nbsp; %s &nbsp;&nbsp; <font color='#00bcd4'>%s</font></html>",
                    t.title, t.deadline, t.status));
            l.setOpaque(true);
            l.setBackground(isSelected ? ACCENT : BG_DARK);
            l.setForeground(TEXT);
            return l;
        }
    }
}