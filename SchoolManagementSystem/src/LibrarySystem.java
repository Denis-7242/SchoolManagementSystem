import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// Library System using HashMap + Stack
public class LibrarySystem extends JFrame {
    private HashMap<Integer, String> books = new HashMap<>();
    private HashMap<Integer, String> borrowed = new HashMap<>();
    private Stack<String> recentActions = new Stack<>();

    private JTextArea output;

    public LibrarySystem() {
        setTitle("Library System (HashMap + Stack)");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input fields
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField idField = new JTextField();
        JTextField titleField = new JTextField();
        JButton addBtn = new JButton("Add Book");
        JButton borrowBtn = new JButton("Borrow Book");
        JButton returnBtn = new JButton("Return Book");
        JButton showBtn = new JButton("Show All Available Books");
        JButton historyBtn = new JButton("Show Recent Actions");

        panel.add(new JLabel("Book ID:"));
        panel.add(idField);
        panel.add(new JLabel("Book Title:"));
        panel.add(titleField);
        panel.add(addBtn);
        panel.add(borrowBtn);

        output = new JTextArea();
        output.setEditable(false);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(returnBtn);
        bottomPanel.add(historyBtn);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);
        add(showBtn, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.WEST);

        // Button Actions
        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String title = titleField.getText();
                if (!books.containsKey(id)) {
                    books.put(id, title);
                    recentActions.push("Added book: " + title);
                    output.setText("Added: " + title);
                } else {
                    output.setText("Book with ID " + id + " already exists.");
                }
            } catch (Exception ex) {
                output.setText("Error: Invalid input.");
            }
        });

        borrowBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                if (books.containsKey(id)) {
                    String title = books.remove(id);
                    borrowed.put(id, title);
                    recentActions.push("Borrowed book: " + title);
                    output.setText("Borrowed: " + title);
                } else {
                    output.setText("Book not available.");
                }
            } catch (Exception ex) {
                output.setText("Error: Invalid input.");
            }
        });

        returnBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                if (borrowed.containsKey(id)) {
                    String title = borrowed.remove(id);
                    books.put(id, title);
                    recentActions.push("Returned book: " + title);
                    output.setText("Returned: " + title);
                } else {
                    output.setText("Book not borrowed.");
                }
            } catch (Exception ex) {
                output.setText("Error: Invalid input.");
            }
        });

        showBtn.addActionListener(e -> {
            if (books.isEmpty()) {
                output.setText("No available books.");
            } else {
                StringBuilder sb = new StringBuilder("Available Books:\n");
                for (Map.Entry<Integer, String> entry : books.entrySet()) {
                    sb.append("ID: ").append(entry.getKey())
                      .append(", Title: ").append(entry.getValue()).append("\n");
                }
                output.setText(sb.toString());
            }
        });

        historyBtn.addActionListener(e -> {
            if (recentActions.isEmpty()) {
                output.setText("No recent actions.");
            } else {
                StringBuilder sb = new StringBuilder("Recent Actions:\n");
                for (String action : recentActions) {
                    sb.append(action).append("\n");
                }
                output.setText(sb.toString());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarySystem().setVisible(true));
    }
}
