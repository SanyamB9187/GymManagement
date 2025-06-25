import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewPlansUI extends JFrame {
    public ViewPlansUI() {
        setTitle("View Membership Plans");
        setSize(600, 300);
        setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("Plan ID");
        model.addColumn("Plan Name");
        model.addColumn("Duration (Months)");
        model.addColumn("Fee");

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM plans")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("plan_id"),
                        rs.getString("plan_name"),
                        rs.getInt("duration_months"),
                        rs.getBigDecimal("fee")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(new JScrollPane(table));
        setVisible(true);
    }
}
