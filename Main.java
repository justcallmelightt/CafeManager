import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CafeManagerFrame frame = new CafeManagerFrame();
            frame.setVisible(true);
        });
    }
}
