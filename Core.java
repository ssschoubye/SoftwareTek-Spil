import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

import java.io.*;
import java.util.logging.*;

    public class Core extends JComponent {

        int x = 100;
        int y = 100;
        int z = 0;
        int bounds = 250;
        boolean turn;




        public Core () {


            Timer timer = new Timer(1, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (y >= x) {
                        turn = true;
                    }
                    if (y <= -x) {
                        turn = false;
                    }
                    if (turn) {
                        y -=7;
                    }
                    repaint();





                }


            });
            timer.start();

        }

        public void paintComponent (Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            super.paintComponent(g);

            g.setColor(Color.GRAY);
            g.fillRect(0,0,500,500);
            g.setColor(Color.BLACK);
            g.drawLine(0,235,1000,235);
            g.drawLine(200,0,200,1000);

            g.setColor(Color.BLACK);
            g.fillOval(150,(bounds-y/2)-8,x,y);
            g.setColor(Color.BLACK);
            g.fillOval(150,(bounds-y/2)-10,x,y);
            g.setColor(Color.BLACK);
            g.fillOval(150,(bounds-y/2)-12,x,y);
            g.setColor(Color.WHITE);
            g.fillOval(150,(bounds-y/2)-14,x,y);
            g.setColor(Color.WHITE);
            g.fillOval(150,(bounds-y/2)-16,x,y);
            g.setColor(Color.WHITE);
            g.fillOval(150,(bounds-y/2)-18,x,y);

            g.setColor(Color.BLACK);
            g.drawOval(150,(bounds-y/2)-18,x,y);



            g.setColor(Color.WHITE);
            g.fillOval(150,(bounds+y/2)-18-(y/10),x,-y);
            g.setColor(Color.WHITE);
            g.fillOval(150,(bounds+y/2)-16-(y/10),x,-y);
            g.setColor(Color.WHITE);
            g.fillOval(150,(bounds+y/2)-14-(y/10),x,-y);
            g.setColor(Color.BLACK);
            g.fillOval(150,(bounds+y/2)-12,x,-y);
            g.setColor(Color.BLACK);
            g.fillOval(150,(bounds+y/2)-10,x,-y);
            g.setColor(Color.BLACK);
            g.fillOval(150,(bounds+y/2)-8,x,-y);


            g.setColor(Color.BLACK);
            g.drawOval(150,(bounds+y/2)-14-(y/10),x,-y);

            g.setColor(Color.WHITE);
            g.drawOval(150,(bounds+y/2)-8+(y/18),x,-y);

   /*     if (y == 0) {
            g.setColor(Color.WHITE);
            g.fillRect(150, 230, 100, 6);
            g.setColor(Color.BLACK);
            g.fillRect(150, 237, 100, 6);
        }
*/

            Toolkit.getDefaultToolkit().sync();

        }
}
