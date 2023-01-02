import java.awt.*;
import java.awt.color.*;
import java.lang.*;

public class Circle {
    private int x;
    int y;
    int width;
    int height;

    public Circle() {
        x = 1;
        y = 1;
        width = 1;
        height = 1;
    }

    public void drawCircle(Graphics c) {
        c.setColor(Color.BLACK);
        c.fillOval(x, y, width, height);
    }

    public void main(String[] args) {
        drawCircle(null);
    }
}