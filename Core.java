import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

public class Core extends JComponent {

int x = 100;
int y = 100;
int bounds = 100;

boolean shrink;
boolean turn;

public Core () {
    Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (x > getWidth() - bounds) {
                shrink = true;
            }
            if (x < 0) {
                shrink = false;
            }

            if (shrink = false) {
                x += 1;
            } else x-=1;

            if (y > getHeight() - bounds) {
                turn = true;
            }
            if (y > 0) {
                turn = false;
            }
            if (turn = true) {
                y += 1;
            } else y-=1;
            repaint();
        }

    });
    timer.start();
}

public void paintComponent (Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    super.paintComponent(g);
    g.setColor(Color.BLUE);
    g.fillOval(x,y,bounds,bounds);
    g2d.dispose();

    Toolkit.getDefaultToolkit().sync();

}



        }
