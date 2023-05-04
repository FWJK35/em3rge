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

        // d.getWorld().addParticle(new Particle(0, 11, 0));
        // d.getWorld().addParticle(new Particle(0, 10, 0));
        d.getWorld().addParticle(new Particle(10, 2, 2, -2));
        
        d.getWorld().addParticle(new Particle(10, 0, 0));
        d.getWorld().addParticle(new Particle(10, 2, 0));
        d.getWorld().addParticle(new Particle(12, 0, 0));
        d.getWorld().addParticle(new Particle(12, 2, 0));
        d.getWorld().addParticle(new Particle(10, 0, 2));
        d.getWorld().addParticle(new Particle(10, 2, 2));
        d.getWorld().addParticle(new Particle(12, 0, 2));
        d.getWorld().addParticle(new Particle(12, 2, 2));

        

        d.renderParticles();
    }
}
