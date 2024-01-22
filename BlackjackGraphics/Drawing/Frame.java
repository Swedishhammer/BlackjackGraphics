package Drawing;
import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame{
    
    Panel panel;
    public Frame(){
        panel = new Panel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }

    
}
