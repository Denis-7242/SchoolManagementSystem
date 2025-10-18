import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class StudentRegistry extends JFrame implements ActionListener {
    private JTextField idField, nameField, courseField;
    private JButton addButton, searchButton, showAllButton;
    private JTextArea displayArea;

    // HashMap to store students (ID -> "Name, Course")
    private HashMap<String, String> studentTable;

    public StudentRegistry() {
        studentTable = new HashMap<>();

        // Frame setup
        setTitle("Student Registry");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel idLabel = new JLabel("Student ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel courseLabel = new JLabel("Course:");

        idField = new JTextField();
        nameField = new JTextField();
        courseField = new JTextField();

        inputPanel.add(idLabel); inputPanel.add(idField);
        inputPanel.add(nameLabel); inputPanel.add(nameField);
        inputPanel.add(courseLabel); inputPanel.add(courseField);

        add(inputPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Student");
        searchButton = new JButton("Search Student");
        showAllButton = new JButton("Show All");

        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(showAllButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        // Add listeners
        addButton.addActionListener(this);
        searchButton.addActionListener(this);
        showAllButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String id = idField.getText();
            String name = nameField.getText();
            String course = courseField.getText();

            if (!id.isEmpty() && !name.isEmpty() && !course.isEmpty()) {
                studentTable.put(id, name + ", " + course);
                displayArea.setText("Student added: " + name + " (" + id + ")");
                idField.setText(""); nameField.setText(""); courseField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
            }
        } 
        else if (e.getSource() == searchButton) {
            String id = idField.getText();
            if (studentTable.containsKey(id)) {
                displayArea.setText("Found: " + studentTable.get(id));
            } else {
                displayArea.setText("No student found with ID " + id);
            }
        } 
        else if (e.getSource() == showAllButton) {
            if (studentTable.isEmpty()) {
                displayArea.setText("No students registered.");
            } else {
                StringBuilder sb = new StringBuilder("All Students:\n");
                for (String key : studentTable.keySet()) {
                    sb.append(key).append(" -> ").append(studentTable.get(key)).append("\n");
                }
                displayArea.setText(sb.toString());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentRegistry().setVisible(true);
        });
    }
}
