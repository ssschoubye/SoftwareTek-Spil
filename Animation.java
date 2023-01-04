import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Animation extends JFrame {
    public Animation() {
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Testanimation");
        setVisible(true);
        add(new Core());
    }
    public static void main (String args []) {
        new Animation();

}


}
