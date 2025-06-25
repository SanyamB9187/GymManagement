import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterMemberUI extends JFrame {

    public RegisterMemberUI() {
        setTitle("Register New Gym Member");
        setSize(550, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 245, 255));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);

        // Heading
        JLabel heading = new JLabel("Register Member", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(new Color(40, 40, 90));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(heading, gbc);

        gbc.gridwidth = 1;

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(labelFont);
        JTextField nameField = new JTextField();
        nameField.setFont(fieldFont);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(labelFont);
        JTextField ageField = new JTextField();
        ageField.setFont(fieldFont);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(labelFont);
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderBox.setFont(fieldFont);

        JLabel planLabel = new JLabel("Plan ID:");
        planLabel.setFont(labelFont);
        JTextField planField = new JTextField();
        planField.setFont(fieldFont);

        JButton submitBtn = new JButton("Register");
        submitBtn.setBackground(new Color(0, 120, 215));
        submitBtn.setForeground(Color.BLACK);
        submitBtn.setFont(buttonFont);
        submitBtn.setFocusPainted(false);
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add components
        gbc.gridx = 0; gbc.gridy = 1; add(nameLabel, gbc);
        gbc.gridx = 1; add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(ageLabel, gbc);
        gbc.gridx = 1; add(ageField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(genderLabel, gbc);
        gbc.gridx = 1; add(genderBox, gbc);

        gbc.gridx = 0; gbc.gridy = 4; add(planLabel, gbc);
        gbc.gridx = 1; add(planField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(submitBtn, gbc);

        // Action listener
        submitBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String planText = planField.getText().trim();
            String gender = (String) genderBox.getSelectedItem();

            if (name.isEmpty() || ageText.isEmpty() || planText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            try {
                int age = Integer.parseInt(ageText);
                int planId = Integer.parseInt(planText);

                try (Connection conn = DBConnection.connect()) {
                    if (conn == null) {
                        JOptionPane.showMessageDialog(this, "Database connection failed.");
                        return;
                    }

                    String sql = "INSERT INTO Members (name, age, gender, plan_id) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, name);
                    stmt.setInt(2, age);
                    stmt.setString(3, gender);
                    stmt.setInt(4, planId);

                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Member registered successfully!");
                        nameField.setText(""); ageField.setText(""); planField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Member registration failed.");
                    }
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Enter valid numbers for age and plan ID.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        // Optional: native look
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        new RegisterMemberUI();
    }
}
