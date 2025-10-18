import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;

public class CourseScheduling extends JFrame implements ActionListener {
    private JTextField studentField, courseField;
    private JButton addButton, processButton, showQueueButton;
    private JTextArea displayArea;

    // Queue to hold scheduling requests
    private Queue<String> scheduleQueue;

    public CourseScheduling() {
        scheduleQueue = new LinkedList<>();

        // Frame setup
        setTitle("Course Scheduling");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel studentLabel = new JLabel("Student Name:");
        JLabel courseLabel = new JLabel("Course:");

        studentField = new JTextField();
        courseField = new JTextField();

        inputPanel.add(studentLabel); inputPanel.add(studentField);
        inputPanel.add(courseLabel); inputPanel.add(courseField);

        add(inputPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Request");
        processButton = new JButton("Process Next");
        showQueueButton = new JButton("Show Queue");

        buttonPanel.add(addButton);
        buttonPanel.add(processButton);
        buttonPanel.add(showQueueButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        // Listeners
        addButton.addActionListener(this);
        processButton.addActionListener(this);
        showQueueButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String student = studentField.getText();
            String course = courseField.getText();

            if (!student.isEmpty() && !course.isEmpty()) {
                scheduleQueue.add(student + " - " + course);
                displayArea.setText("Request added: " + student + " for " + course);
                studentField.setText("");
                courseField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
            }
        } 
        else if (e.getSource() == processButton) {
            if (!scheduleQueue.isEmpty()) {
                String request = scheduleQueue.poll(); // Remove first in line
                displayArea.setText("Processed: " + request);
            } else {
                displayArea.setText("No requests to process.");
            }
        } 
        else if (e.getSource() == showQueueButton) {
            if (scheduleQueue.isEmpty()) {
                displayArea.setText("Queue is empty.");
            } else {
                StringBuilder sb = new StringBuilder("Current Queue:\n");
                for (String req : scheduleQueue) {
                    sb.append(req).append("\n");
                }
                displayArea.setText(sb.toString());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CourseScheduling().setVisible(true);
        });
    }
}
