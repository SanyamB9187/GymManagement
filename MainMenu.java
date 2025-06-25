import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Gym Management System");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // LEFT PANEL (blue branding)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 102, 204));
        leftPanel.setPreferredSize(new Dimension(300, 0));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel iconLabel = new JLabel(new ImageIcon("user_icon.png")); // Optional image
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel titleLabel = new JLabel("Gym Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel subTitleLabel = new JLabel("Manage Everything Easily");
        subTitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subTitleLabel.setForeground(Color.WHITE);
        subTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(iconLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(subTitleLabel);
        leftPanel.add(Box.createVerticalGlue());

        // RIGHT PANEL (menu buttons)
        JPanel rightPanel = new JPanel(new GridLayout(4, 2, 15, 15));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        rightPanel.setBackground(Color.WHITE);

        String[] labels = {
                "Register Member", "Mark Attendance", "View Members", "View Membership Plans",
                "View Attendance", "Assign Trainer", "View Trainers", "View Member-Trainer"
        };

        Class<?>[] targets = {
                RegisterMemberUI.class, AttendanceUI.class, ViewMembersUI.class, ViewPlansUI.class,
                ViewAttendanceUI.class, AssignTrainerUI.class, ViewTrainersUI.class, ViewMemberTrainerUI.class
        };

        Font btnFont = new Font("Segoe UI", Font.PLAIN, 15);

        for (int i = 0; i < labels.length; i++) {
            JButton btn = new JButton(labels[i]);
            btn.setFont(btnFont);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(0, 102, 204));
            btn.setForeground(Color.BLACK);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            int index = i;
            btn.addActionListener(e -> {
                try {
                    targets[index].getDeclaredConstructor().newInstance();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            // Hover effect
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(new Color(0, 90, 180));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(new Color(0, 102, 204));
                }
            });

            rightPanel.add(btn);
        }

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        new MainMenu();
    }
}
