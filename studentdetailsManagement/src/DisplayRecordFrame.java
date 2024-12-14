import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class DisplayRecordFrame extends JFrame {

    private JTextField textField;
    private JTable table;
    private JScrollPane scrollPane;

    public DisplayRecordFrame() {
        setTitle("Display Record");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create input field and button
        JLabel label = new JLabel("Enter Student ID:");
        textField = new JTextField(10);
        JButton button = new JButton("Fetch Details");

        button.addActionListener(e -> fetchStudentDetails(textField.getText()));

        // Create a panel for the input and button
        JPanel inputPanel = new JPanel();
        inputPanel.add(label);
        inputPanel.add(textField);
        inputPanel.add(button);

        // Table model with columns
        String[] columnNames = {"ID", "Name", "Class", "Marks", "Gender"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true); // Make the table fill the available space

        // Add the table to a scroll pane for scrolling
        scrollPane = new JScrollPane(table);
        
        // Layout setup
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);  // Add table scroll pane to the center

        setVisible(true);
    }

    private void fetchStudentDetails(String studentId) {
        // Fetch data and populate the table
        String query = "SELECT * FROM Studentdetails WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/KU_db", "root", "Hemanth");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);  // Clear existing rows

            if (rs.next()) {
                // Add row to the table for the student details
                Object[] row = {
                    rs.getInt("ID"),
                    rs.getString("Name"),
                    rs.getString("Class"),
                    rs.getInt("Marks"),
                    rs.getString("Gender")
                };
                model.addRow(row);  // Add row to the table
            } else {
                JOptionPane.showMessageDialog(this, "No record found for ID: " + studentId);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new DisplayRecordFrame();
    }
}
