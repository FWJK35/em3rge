/*
 * 
 */

import java.awt.*;
import javax.swing.*;

public class Window extends JPanel {

    public static final Dimension WINDOW_DIMENSION = new Dimension(400, 400);
    public static final Dimension DISPLAY_DIMENSION = new Dimension(1080, 1080);


    public static void main(String[] args) {
        Window w = new Window();
    }

    public Window() {

        JFrame frame = new JFrame("em3rge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(WINDOW_DIMENSION);
        frame.pack();
        
        
        JPanel graphicsElement = new JPanel();
        frame.getContentPane().add(graphicsElement, BorderLayout.CENTER);
        Graphics g = graphicsElement.getGraphics();
        g.fillRect(100, 100, 100, 100);
        g.dispose();

        frame.setVisible(true);

        

    }
}
