package Drawing;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Panel extends JPanel{
    int x1,y1,x2,y2;
    public Panel(){
        //this.setPreferredSize(new Dimension(500,500));
        this.x1 = 0;
        this.y1 = 0;
        this.x2 = 500;
        this.y2 = 500;
    }
    Panel(int x1, int y1, int x2, int y2){
        //this.setPreferredSize(new Dimension(500,500));
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D)g;

        g2D.drawLine(x1,y1,x2,y2);
    }
}
