import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewAttendanceUI extends JFrame {
    public ViewAttendanceUI() {
        setTitle("Attendance Records");
        setSize(600, 300);
        setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("Attendance ID");
        model.addColumn("Member ID");
        model.addColumn("Date");
        model.addColumn("Check in time");

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM attendance")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("attendance_id"),
                        rs.getInt("member_id"),
                        rs.getDate("date"),
                        rs.getDate("check_in_time")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(new JScrollPane(table));
        setVisible(true);
    }
}
