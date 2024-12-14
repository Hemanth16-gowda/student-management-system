import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateRecordFrame extends JFrame {

    private JTextField textFieldId, textFieldValue;
    private JCheckBox chkName, chkClass, chkMarks, chkGender;
    private JButton updateButton;

    public UpdateRecordFrame() {
        setTitle("Update Record");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute layout

        // Components for input and update
        JLabel labelId = new JLabel("Enter Student ID:");
        labelId.setBounds(50, 30, 150, 30);
        textFieldId = new JTextField();
        textFieldId.setBounds(200, 30, 200, 30);

        JLabel labelColumn = new JLabel("Select Column to Update:");
        labelColumn.setBounds(50, 80, 200, 30);

        // Checkboxes for columns
        chkName = new JCheckBox("Name");
        chkName.setBounds(50, 120, 100, 30);
        chkClass = new JCheckBox("Class");
        chkClass.setBounds(150, 120, 100, 30);
        chkMarks = new JCheckBox("Marks");
        chkMarks.setBounds(250, 120, 100, 30);
        chkGender = new JCheckBox("Gender");
        chkGender.setBounds(350, 120, 100, 30);

        // Input field for the new value
        JLabel labelValue = new JLabel("Enter New Value:");
        labelValue.setBounds(50, 180, 150, 30);
        textFieldValue = new JTextField();
        textFieldValue.setBounds(200, 180, 200, 30);

        // Update button
        updateButton = new JButton("Update Record");
        updateButton.setBounds(200, 250, 150, 40);
        updateButton.addActionListener(e -> updateRecord());

        // Add components to frame
        add(labelId);
        add(textFieldId);
        add(labelColumn);
        add(chkName);
        add(chkClass);
        add(chkMarks);
        add(chkGender);
        add(labelValue);
        add(textFieldValue);
        add(updateButton);
    }

    private void updateRecord() {
        String studentId = textFieldId.getText().trim();
        String newValue = textFieldValue.getText().trim();

        // Validate input
        if (studentId.isEmpty() || newValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Determine which column is selected
        String columnToUpdate = null;
        if (chkName.isSelected()) columnToUpdate = "Name";
        if (chkClass.isSelected()) columnToUpdate = "Class";
        if (chkMarks.isSelected()) columnToUpdate = "Marks";
        if (chkGender.isSelected()) columnToUpdate = "Gender";

        // Ensure only one checkbox is selected
        int selectedCount = (chkName.isSelected() ? 1 : 0) + 
                            (chkClass.isSelected() ? 1 : 0) + 
                            (chkMarks.isSelected() ? 1 : 0) + 
                            (chkGender.isSelected() ? 1 : 0);
        if (selectedCount != 1) {
            JOptionPane.showMessageDialog(this, "Please select exactly one column to update.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update the database
        String query = "UPDATE Studentdetails SET " + columnToUpdate + " = ? WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/KU_db", "root", "Hemanth");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newValue);
            stmt.setString(2, studentId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Record updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No record found for ID: " + studentId);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new UpdateRecordFrame().setVisible(true);
    }
}
