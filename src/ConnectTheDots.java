import javax.swing.*;
import java.awt.*;

public class ConnectTheDots extends JPanel {
private  final int FRAME_X = 512;
private  final int FRAME_Y = 512;

    public ConnectTheDots() {

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(FRAME_X, FRAME_Y);
    }

    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        int rectW = 64;
        int rectH = 64;
        Shape rect = new Rectangle(
                (FRAME_X / 2) - (rectW / 2),
                (FRAME_Y / 2) - (rectH / 2),
                rectW,
                rectH);

        for (int r = FRAME_X / 2; r <= FRAME_X; r -= 32) {
            g.draw(new Rectangle(
                    (FRAME_X / 2) - (r / 2),
                    (FRAME_Y / 2) - (r / 2),
                    r,
                    r));
        }
    }

}

class Rect {

    private int x = 50;
    private int y = 50;
    private int w = 100;
    private int h = 100;

    public void setX(int xPos){
        this.x = xPos;
    }

    public int getX(){
        return x;
    }

    public void setY(int yPos){
        this.y = yPos;
    }

    public int getY(){
        return y;
    }

    public int getW(){
        return w;
    }

    public int getH(){
        return h;
    }

    public void paintRect(Graphics g){
//        g.setColor(Color.RED);
//        g.fillRect(x, y, w, h);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, w, h);
    }
}