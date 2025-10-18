import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {
    private JButton studentRegistryBtn, courseSchedulingBtn, feeTrackingBtn, librarySystemBtn, performanceAnalyticsBtn;

    public Dashboard() {
        // Frame setup
        setTitle("School Management System - Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));
        setLocationRelativeTo(null);

        // Buttons for modules
        studentRegistryBtn = new JButton("Student Registry");
        courseSchedulingBtn = new JButton("Course Scheduling");
        feeTrackingBtn = new JButton("Fee Tracking");
        librarySystemBtn = new JButton("Library System");
        performanceAnalyticsBtn = new JButton("Performance Analytics");

        add(studentRegistryBtn);
        add(courseSchedulingBtn);
        add(feeTrackingBtn);
        add(librarySystemBtn);
        add(performanceAnalyticsBtn);

        // Action listeners
        studentRegistryBtn.addActionListener(this);
        courseSchedulingBtn.addActionListener(this);
        feeTrackingBtn.addActionListener(this);
        librarySystemBtn.addActionListener(this);
        performanceAnalyticsBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == studentRegistryBtn) {
            new StudentRegistry().setVisible(true);
        } else if (e.getSource() == courseSchedulingBtn) {
            new CourseScheduling().setVisible(true);
        } else if (e.getSource() == feeTrackingBtn) {
            new FeeTracking().setVisible(true);
        } else if (e.getSource() == librarySystemBtn) {
            new LibrarySystem().setVisible(true);
        } else if (e.getSource() == performanceAnalyticsBtn) {
            new PerformanceAnalytics().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Dashboard().setVisible(true);
        });
    }
}
