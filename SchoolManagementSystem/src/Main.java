public class Main {
    public static void main(String[] args) {
        // Start the GUI safely on the Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Open the Login window first
            new LoginSystem().setVisible(true);
        });
    }
}
