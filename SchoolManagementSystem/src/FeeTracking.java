import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Node for AVL Tree
class AVLNode {
    int id;
    String name;
    double fee;
    int height;
    AVLNode left, right;

    public AVLNode(int id, String name, double fee) {
        this.id = id;
        this.name = name;
        this.fee = fee;
        height = 1;
    }
}

// AVL Tree Implementation
class AVLTree {
    private AVLNode root;

    int height(AVLNode N) {
        return (N == null) ? 0 : N.height;
    }

    int getBalance(AVLNode N) {
        return (N == null) ? 0 : height(N.left) - height(N.right);
    }

    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    AVLNode insert(AVLNode node, int id, String name, double fee) {
        if (node == null) return new AVLNode(id, name, fee);

        if (id < node.id)
            node.left = insert(node.left, id, name, fee);
        else if (id > node.id)
            node.right = insert(node.right, id, name, fee);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        // Rotations
        if (balance > 1 && id < node.left.id) return rightRotate(node);
        if (balance < -1 && id > node.right.id) return leftRotate(node);
        if (balance > 1 && id > node.left.id) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && id < node.right.id) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    public void insert(int id, String name, double fee) {
        root = insert(root, id, name, fee);
    }

    public AVLNode search(int id) {
        return search(root, id);
    }

    private AVLNode search(AVLNode node, int id) {
        if (node == null || node.id == id) return node;
        if (id < node.id) return search(node.left, id);
        return search(node.right, id);
    }

    public String inorder() {
        StringBuilder sb = new StringBuilder();
        inorder(root, sb);
        return sb.toString();
    }

    private void inorder(AVLNode node, StringBuilder sb) {
        if (node != null) {
            inorder(node.left, sb);
            sb.append("ID: ").append(node.id).append(", Name: ").append(node.name)
              .append(", Fee: ").append(node.fee).append("\n");
            inorder(node.right, sb);
        }
    }
}

// GUI for Fee Tracking
public class FeeTracking extends JFrame {
    private AVLTree avl = new AVLTree();
    private JTextArea output;

    public FeeTracking() {
        setTitle("Fee Tracking (AVL Tree)");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2));
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField feeField = new JTextField();
        JButton insertBtn = new JButton("Insert Fee Record");
        JButton searchBtn = new JButton("Search by ID");
        JButton showBtn = new JButton("Show All Records");

        panel.add(new JLabel("Student ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Fee:"));
        panel.add(feeField);
        panel.add(insertBtn);
        panel.add(searchBtn);

        output = new JTextArea();
        output.setEditable(false);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);
        add(showBtn, BorderLayout.SOUTH);

        insertBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                double fee = Double.parseDouble(feeField.getText());
                avl.insert(id, name, fee);
                output.setText("Inserted: " + name + " with Fee " + fee);
            } catch (Exception ex) {
                output.setText("Error: Invalid input.");
            }
        });

        searchBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                AVLNode node = avl.search(id);
                if (node != null)
                    output.setText("Found: " + node.name + " owes " + node.fee);
                else
                    output.setText("Record not found.");
            } catch (Exception ex) {
                output.setText("Error: Invalid input.");
            }
        });

        showBtn.addActionListener(e -> {
            output.setText("All Records:\n" + avl.inorder());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FeeTracking().setVisible(true));
    }
}
