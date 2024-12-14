import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class InsertRecordFrame extends JFrame {

    private JTextField textFieldId, textFieldName, textFieldClass, textFieldMarks;
    private JComboBox<String> genderComboBox; // ComboBox for gender selection
    private JButton insertButton;

    public InsertRecordFrame() {
        setTitle("Insert Record");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute layout

        // Labels and text fields for input
        JLabel labelId = new JLabel("Enter Student ID:");
        labelId.setBounds(50, 30, 150, 30);
        textFieldId = new JTextField();
        textFieldId.setBounds(200, 30, 200, 30);

        JLabel labelName = new JLabel("Enter Name:");
        labelName.setBounds(50, 80, 150, 30);
        textFieldName = new JTextField();
        textFieldName.setBounds(200, 80, 200, 30);

        JLabel labelClass = new JLabel("Enter Class (A1-A5):");
        labelClass.setBounds(50, 130, 150, 30);
        textFieldClass = new JTextField();
        textFieldClass.setBounds(200, 130, 200, 30);

        JLabel labelMarks = new JLabel("Enter Marks:");
        labelMarks.setBounds(50, 180, 150, 30);
        textFieldMarks = new JTextField();
        textFieldMarks.setBounds(200, 180, 200, 30);

        JLabel labelGender = new JLabel("Select Gender:");
        labelGender.setBounds(50, 230, 150, 30);
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"}); // Gender ComboBox
        genderComboBox.setBounds(200, 230, 200, 30);

        // Insert button
        insertButton = new JButton("Insert Record");
        insertButton.setBounds(200, 290, 150, 40);
        insertButton.addActionListener(e -> insertRecord());

        // Add components to frame
        add(labelId);
        add(textFieldId);
        add(labelName);
        add(textFieldName);
        add(labelClass);
        add(textFieldClass);
        add(labelMarks);
        add(textFieldMarks);
        add(labelGender);
        add(genderComboBox);
        add(insertButton);
    }

    private void insertRecord() {
        String studentId = textFieldId.getText().trim();
        String studentName = textFieldName.getText().trim();
        String studentClass = textFieldClass.getText().trim();
        String studentMarks = textFieldMarks.getText().trim();
        String studentGender = (String) genderComboBox.getSelectedItem(); // Get selected gender

        // Validate input
        if (studentId.isEmpty() || studentName.isEmpty() || studentClass.isEmpty() || studentMarks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate class value
        if (!studentClass.matches("A[1-5]")) {
            JOptionPane.showMessageDialog(this, "Class must be A1, A2, A3, A4, or A5.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int marks = Integer.parseInt(studentMarks);
            if (marks < 0 || marks > 100) {
                JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Marks must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Insert into database
        String query = "INSERT INTO Studentdetails (ID, Name, Class, Marks, Gender) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/KU_db", "root", "Hemanth");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(studentId)); // Convert student ID to int
            stmt.setString(2, studentName);
            stmt.setString(3, studentClass);
            stmt.setInt(4, Integer.parseInt(studentMarks)); // Convert marks to int
            stmt.setString(5, studentGender); // Use gender from ComboBox

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Record inserted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to insert record.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new InsertRecordFrame().setVisible(true);
    }
}
