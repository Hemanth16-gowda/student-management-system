import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DeleteRecordFrame extends JFrame {

    private JTextField textFieldId;
    private JButton deleteButton;

    public DeleteRecordFrame() {
        setTitle("Delete Record");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute layout

        // Label and text field for student ID input
        JLabel labelId = new JLabel("Enter Student ID to Delete:");
        labelId.setBounds(50, 50, 200, 30);
        textFieldId = new JTextField();
        textFieldId.setBounds(250, 50, 200, 30);

        // Delete button
        deleteButton = new JButton("Delete Record");
        deleteButton.setBounds(200, 150, 200, 40);
        deleteButton.addActionListener(e -> deleteRecord());

        // Add components to frame
        add(labelId);
        add(textFieldId);
        add(deleteButton);
    }

    private void deleteRecord() {
        String studentId = textFieldId.getText().trim();

        // Validate input
        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a student ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if the ID exists in the database
        String checkQuery = "SELECT * FROM Studentdetails WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/KU_db", "root", "Hemanth");
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setString(1, studentId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "No record found for the given student ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Proceed to delete the record
        String deleteQuery = "DELETE FROM Studentdetails WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/KU_db", "root", "Hemanth");
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {

            deleteStmt.setString(1, studentId);
            int rowsAffected = deleteStmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Record deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete record.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new DeleteRecordFrame().setVisible(true);
    }
}
