import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class StudentRecord implements Comparable<StudentRecord> {
    String name;
    double average;

    public StudentRecord(String name, double average) {
        this.name = name;
        this.average = average;
    }

    @Override
    public int compareTo(StudentRecord other) {
        return Double.compare(other.average, this.average); // Max-Heap
    }
}

public class PerformanceAnalytics extends JFrame {
    private JTextArea output;

    private java.util.List<String> students = new ArrayList<>();
    private java.util.List<String> courses = new ArrayList<>();
    private Map<String, Map<String, Double>> grades = new HashMap<>();

    public PerformanceAnalytics() {
        setTitle("Performance Analytics (Matrix + Heap)");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        output = new JTextArea();
        output.setEditable(false);

        // Panels
        JPanel topPanel = new JPanel(new GridLayout(4, 2));
        JTextField studentField = new JTextField();
        JButton addStudentBtn = new JButton("Add Student");
        JTextField courseField = new JTextField();
        JButton addCourseBtn = new JButton("Add Course");

        topPanel.add(new JLabel("Student Name:"));
        topPanel.add(studentField);
        topPanel.add(addStudentBtn);
        topPanel.add(new JLabel("Course Name:"));
        topPanel.add(courseField);
        topPanel.add(addCourseBtn);

        // Grade input
        JComboBox<String> studentBox = new JComboBox<>();
        JComboBox<String> courseBox = new JComboBox<>();
        JTextField gradeField = new JTextField();
        JButton addGradeBtn = new JButton("Add Grade");

        JPanel midPanel = new JPanel(new GridLayout(2, 3));
        midPanel.add(new JLabel("Select Student:"));
        midPanel.add(studentBox);
        midPanel.add(new JLabel("Select Course:"));
        midPanel.add(courseBox);
        midPanel.add(new JLabel("Grade:"));
        midPanel.add(gradeField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton showBtn = new JButton("Show All Grades");
        JButton topBtn = new JButton("Show Top Performer");
        buttonPanel.add(addGradeBtn);
        buttonPanel.add(showBtn);
        buttonPanel.add(topBtn);

        // Layout
        add(topPanel, BorderLayout.NORTH);
        add(midPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(new JScrollPane(output), BorderLayout.EAST);

        // Actions
        addStudentBtn.addActionListener(e -> {
            String name = studentField.getText().trim();
            if (!name.isEmpty() && !students.contains(name)) {
                students.add(name);
                studentBox.addItem(name);
                grades.put(name, new HashMap<>());
                output.setText("Added student: " + name);
            } else {
                output.setText("Invalid or duplicate student.");
            }
        });

        addCourseBtn.addActionListener(e -> {
            String course = courseField.getText().trim();
            if (!course.isEmpty() && !courses.contains(course)) {
                courses.add(course);
                courseBox.addItem(course);
                output.setText("Added course: " + course);
            } else {
                output.setText("Invalid or duplicate course.");
            }
        });

        addGradeBtn.addActionListener(e -> {
            try {
                String student = (String) studentBox.getSelectedItem();
                String course = (String) courseBox.getSelectedItem();
                double grade = Double.parseDouble(gradeField.getText());

                if (student != null && course != null) {
                    grades.get(student).put(course, grade);
                    output.setText("Added grade " + grade + " for " + student + " in " + course);
                } else {
                    output.setText("Please select valid student and course.");
                }
            } catch (Exception ex) {
                output.setText("Error: Enter a valid grade number.");
            }
        });

        showBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Grades Matrix:\n");
            for (String s : students) {
                sb.append(s).append(": ");
                for (String c : courses) {
                    double g = grades.get(s).getOrDefault(c, 0.0);
                    sb.append(c).append("=").append(g).append("  ");
                }
                sb.append("\n");
            }
            output.setText(sb.toString());
        });

        topBtn.addActionListener(e -> {
            PriorityQueue<StudentRecord> heap = new PriorityQueue<>();
            for (String s : students) {
                double sum = 0;
                int count = 0;
                for (double g : grades.get(s).values()) {
                    if (g > 0) {
                        sum += g;
                        count++;
                    }
                }
                if (count > 0) {
                    heap.add(new StudentRecord(s, sum / count));
                }
            }
            if (!heap.isEmpty()) {
                StudentRecord top = heap.poll();
                output.setText("Top Performer: " + top.name + " with Average " + top.average);
            } else {
                output.setText("No grades available yet.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PerformanceAnalytics().setVisible(true));
    }
}
