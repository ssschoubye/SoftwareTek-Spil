import javax.swing.*;
import java.awt.*;

public class Coinflip extends JFrame {
    public Coinflip() {
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setTitle("Testanimation");
        setVisible(true);
        add(new Core());


    }

}
