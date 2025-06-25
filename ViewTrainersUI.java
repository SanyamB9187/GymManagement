import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ViewTrainersUI extends JFrame {
    public ViewTrainersUI() {
        setTitle("View Trainers");
        setSize(600, 300);
        setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("Trainer ID");
        model.addColumn("Name");
        model.addColumn("Specialty");

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Trainers")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("trainer_id"),
                        rs.getString("name"),
                        rs.getString("specialization")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(new JScrollPane(table));
        setVisible(true);
    }
}
