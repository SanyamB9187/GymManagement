import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewMemberTrainerUI extends JFrame {

    public ViewMemberTrainerUI() {
        setTitle("Member-Trainer Assignments");
        setSize(700, 350);
        setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("Member ID");
        model.addColumn("Member Name");
        model.addColumn("Trainer ID");
        model.addColumn("Trainer Name");

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT \n" +
                             "    m.member_id,\n" +
                             "    m.name AS member_name,\n" +
                             "    t.trainer_id,\n" +
                             "    t.name AS trainer_name\n" +
                             "FROM \n" +
                             "    member_trainer mt\n" +
                             "JOIN \n" +
                             "    members m ON mt.member_id = m.member_id\n" +
                             "JOIN \n" +
                             "    trainers t ON mt.trainer_id = t.trainer_id;"
             )) {

            while (rs.next()) {
                int memberId = rs.getInt("member_id");
                String memberName = rs.getString("member_name");
                int trainerId = rs.getInt("trainer_id");
                String trainerName = rs.getString("trainer_name");

                model.addRow(new Object[]{
                        memberId,
                        memberName,
                        (rs.wasNull() ? "—" : trainerId),
                        (trainerName == null ? "Unassigned" : trainerName)
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }

        add(new JScrollPane(table));
        setVisible(true);
    }
}
