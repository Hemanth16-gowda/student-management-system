import javax.swing.*;
import java.awt.*;

public class StudentManagementSystem {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Student Management System");
        frame.setSize(600, 400); // Set frame size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // Use absolute layout

        // Add heading
        JLabel heading = new JLabel("Student Management System", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setBounds(100, 20, 400, 30); // Position the heading

        // Create buttons
        JButton displayButton = new JButton("Display Record");
        JButton customizeButton = new JButton("Customized Display");
        JButton insertButton = new JButton("Insert Record");
        JButton updateButton = new JButton("Update Record");
        JButton deleteButton = new JButton("Delete Record");

        // Button dimensions
        int buttonWidth = 200;
        int buttonHeight = 40;

        // Position calculations
        int frameWidth = 600;
        int frameHeight = 400;
        int horizontalSpacing = 20; // Space between buttons horizontally
        int verticalSpacing = 20;  // Space between rows

        // Center alignment base
        int col1X = frameWidth / 4 - buttonWidth / 2; // X-coordinate for left column
        int col2X = 3 * frameWidth / 4 - buttonWidth / 2; // X-coordinate for right column
        int row1Y = 80; // Y-coordinate for first row (below the heading)
        int row2Y = row1Y + buttonHeight + verticalSpacing; // Y-coordinate for second row
        int centerY = row2Y + buttonHeight + verticalSpacing; // Y-coordinate for Delete button

        // Set button positions
        displayButton.setBounds(col1X, row1Y, buttonWidth, buttonHeight);
        customizeButton.setBounds(col2X, row1Y, buttonWidth, buttonHeight);
        insertButton.setBounds(col1X, row2Y, buttonWidth, buttonHeight);
        updateButton.setBounds(col2X, row2Y, buttonWidth, buttonHeight);
        deleteButton.setBounds(frameWidth / 2 - buttonWidth / 2, centerY, buttonWidth, buttonHeight);

        // Apply styles to buttons
        setButtonStyle(displayButton, Color.CYAN, Color.BLACK);
        setButtonStyle(customizeButton, Color.PINK, Color.BLACK);
        setButtonStyle(insertButton, Color.GREEN, Color.BLACK);
        setButtonStyle(updateButton, Color.ORANGE, Color.BLACK);
        setButtonStyle(deleteButton, Color.RED, Color.WHITE);

        // Add heading and buttons to the frame
        frame.add(heading);
        frame.add(displayButton);
        frame.add(customizeButton);
        frame.add(insertButton);
        frame.add(updateButton);
        frame.add(deleteButton);

        // Add action listeners to open new frames
        addAction(displayButton, "Display Record");
        addAction(customizeButton, "Customized Display");
        addAction(insertButton, "Insert Record");
        addAction(updateButton, "Update Record"); // Add action listener for Update Record
        addAction(deleteButton, "Delete Record");

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to set button style
    private static void setButtonStyle(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }

    // Method to add an action listener to a button
    private static void addAction(JButton button, String title) {
        button.addActionListener(e -> {
            if (title.equals("Display Record")) {
                // Open the Display Record JFrame
                new DisplayRecordFrame().setVisible(true);
            } else if (title.equals("Customized Display")) {
                // Open Customized Display JFrame
                new CustomizedDisplayFrame().setVisible(true);
            } else if (title.equals("Insert Record")) {
                // Open Insert Record JFrame
                new InsertRecordFrame().setVisible(true);
            } else if (title.equals("Update Record")) {
                // Open Update Record JFrame
                new UpdateRecordFrame().setVisible(true);
            } else if (title.equals("Delete Record")) {
                // Open Delete Record JFrame
                new DeleteRecordFrame().setVisible(true);
            }
        });
    }
}
