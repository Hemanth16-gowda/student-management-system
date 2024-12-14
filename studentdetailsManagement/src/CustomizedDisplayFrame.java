import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class CustomizedDisplayFrame extends JFrame {

    private JTextField textFieldId;
    private JCheckBox checkBoxId, checkBoxName, checkBoxClass, checkBoxMarks, checkBoxGender;
    private JButton fetchButton;
    private JTable table;
    private JScrollPane scrollPane;

    public CustomizedDisplayFrame() {
        setTitle("Customized Display");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute layout

        // Label and text field for student ID input
        JLabel labelId = new JLabel("Enter Student ID:");
        labelId.setBounds(50, 30, 150, 30);
        textFieldId = new JTextField();
        textFieldId.setBounds(200, 30, 200, 30);

        // Checkboxes for selecting which columns to display
        checkBoxId = new JCheckBox("ID");
        checkBoxId.setBounds(50, 80, 100, 30);
        checkBoxId.setSelected(true); // By default, select ID checkbox

        checkBoxName = new JCheckBox("Name");
        checkBoxName.setBounds(150, 80, 100, 30);
        checkBoxName.setSelected(true); // By default, select Name checkbox

        checkBoxClass = new JCheckBox("Class");
        checkBoxClass.setBounds(250, 80, 100, 30);
        checkBoxClass.setSelected(true); // By default, select Class checkbox

        checkBoxMarks = new JCheckBox("Marks");
        checkBoxMarks.setBounds(350, 80, 100, 30);
        checkBoxMarks.setSelected(true); // By default, select Marks checkbox

        checkBoxGender = new JCheckBox("Gender");
        checkBoxGender.setBounds(450, 80, 100, 30);
        checkBoxGender.setSelected(true); // By default, select Gender checkbox

        // Button to fetch the student details
        fetchButton = new JButton("Fetch Details");
        fetchButton.setBounds(200, 120, 150, 40);
        fetchButton.addActionListener(e -> fetchDetails());

        // Table to display the results
        table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 170, 500, 150);

        // Add components to frame
        add(labelId);
        add(textFieldId);
        add(checkBoxId);
        add(checkBoxName);
        add(checkBoxClass);
        add(checkBoxMarks);
        add(checkBoxGender);
        add(fetchButton);
        add(scrollPane);
    }

    private void fetchDetails() {
        String studentId = textFieldId.getText().trim();
        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a student ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Construct the SQL query based on selected checkboxes
        StringBuilder query = new StringBuilder("SELECT ");
        if (checkBoxId.isSelected()) {
            query.append("ID, ");
        }
        if (checkBoxName.isSelected()) {
            query.append("Name, ");
        }
        if (checkBoxClass.isSelected()) {
            query.append("Class, ");
        }
        if (checkBoxMarks.isSelected()) {
            query.append("Marks, ");
        }
        if (checkBoxGender.isSelected()) {
            query.append("Gender, ");
        }

        // Remove last comma and space
        query.setLength(query.length() - 2);

        query.append(" FROM Studentdetails WHERE ID = ?");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/KU_db", "root", "Hemanth");
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            stmt.setString(1, studentId);

            ResultSet rs = stmt.executeQuery();

            // Check if there is any data returned
            if (rs.next()) {
                // Dynamically create the table model based on selected columns
                DefaultTableModel model = new DefaultTableModel();
                if (checkBoxId.isSelected()) model.addColumn("ID");
                if (checkBoxName.isSelected()) model.addColumn("Name");
                if (checkBoxClass.isSelected()) model.addColumn("Class");
                if (checkBoxMarks.isSelected()) model.addColumn("Marks");
                if (checkBoxGender.isSelected()) model.addColumn("Gender");

                // Add the row data to the table
                Object[] row = new Object[model.getColumnCount()];
                int columnIndex = 0;

                if (checkBoxId.isSelected()) row[columnIndex++] = rs.getInt("ID");
                if (checkBoxName.isSelected()) row[columnIndex++] = rs.getString("Name");
                if (checkBoxClass.isSelected()) row[columnIndex++] = rs.getString("Class");
                if (checkBoxMarks.isSelected()) row[columnIndex++] = rs.getInt("Marks");
                if (checkBoxGender.isSelected()) row[columnIndex++] = rs.getString("Gender");

                model.addRow(row);

                table.setModel(model); // Set the table model to display data
            } else {
                JOptionPane.showMessageDialog(this, "No student found with the given ID.", "No Data", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new CustomizedDisplayFrame().setVisible(true);
    }
}
