import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AttendanceUI extends JFrame {
    public AttendanceUI() {
        setTitle("Mark Attendance");
        setSize(400, 200);
        setLayout(new GridLayout(3, 1));

        JComboBox<String> memberBox = new JComboBox<>();

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT member_id, name FROM Members")) {
            while (rs.next()) {
                memberBox.addItem(rs.getInt("member_id") + " - " + rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JButton checkInBtn = new JButton("Check In");

        add(new JLabel("Select Member:"));
        add(memberBox);
        add(checkInBtn);

        checkInBtn.addActionListener(e -> {
            try (Connection conn = DBConnection.connect()) {
                String selected = (String) memberBox.getSelectedItem();
                int memberId = Integer.parseInt(selected.split(" - ")[0]);

                String sql = "INSERT INTO Attendance (member_id) VALUES (?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, memberId);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Attendance marked!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
