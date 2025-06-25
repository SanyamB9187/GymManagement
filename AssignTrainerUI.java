import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AssignTrainerUI extends JFrame {
    public AssignTrainerUI() {
        setTitle("Assign Trainer");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel memberLabel = new JLabel("Member ID:");
        JTextField memberField = new JTextField();

        JLabel trainerLabel = new JLabel("Trainer ID:");
        JTextField trainerField = new JTextField();

        JButton assignBtn = new JButton("Assign");

        add(memberLabel); add(memberField);
        add(trainerLabel); add(trainerField);
        add(new JLabel()); add(assignBtn);

        assignBtn.addActionListener(e -> {
            int memberId = Integer.parseInt(memberField.getText());
            int trainerId = Integer.parseInt(trainerField.getText());

            try (Connection conn = DBConnection.connect()) {
                PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE member_trainer SET trainer_id = ? WHERE member_id = ?"
                );
                stmt.setInt(1, trainerId);
                stmt.setInt(2, memberId);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Trainer assigned successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Member not found.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
