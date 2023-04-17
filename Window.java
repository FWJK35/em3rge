/*
 * 
 */

import java.awt.*;
import javax.swing.*;

public class Window extends JPanel {

    public static final Dimension WINDOW_DIMENSION = new Dimension(800, 600);
    public static final Dimension DISPLAY_DIMENSION = new Dimension(800, 600);


    public static void main(String[] args) {
        Window w = new Window();
    }

    public Window() {
        JFrame frame = new JFrame("em3rge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_DIMENSION);
        Display d = new Display();
        frame.add(d);
        frame.setVisible(true);

        d.getWorld().addParticle(new Particle(0, 10, 0));
        d.getWorld().addParticle(new Particle(2, 10, 0));
        d.getWorld().addParticle(new Particle(0, 12, 0));
        d.getWorld().addParticle(new Particle(2, 12, 0));
        d.getWorld().addParticle(new Particle(0, 10, 2));
        d.getWorld().addParticle(new Particle(2, 10, 2));
        d.getWorld().addParticle(new Particle(0, 12, 2));
        d.getWorld().addParticle(new Particle(2, 12, 2));

        d.getWorld().addParticle(new Particle(0, 0, -10));
    }
}
