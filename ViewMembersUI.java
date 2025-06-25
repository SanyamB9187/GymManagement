import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewMembersUI extends JFrame {
    public ViewMembersUI() {
        setTitle("View Members");
        setSize(600, 300);
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Name", "Age", "Gender", "Join Date", "Plan ID"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Members")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("member_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getDate("join_date"),
                        rs.getInt("plan_id")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
