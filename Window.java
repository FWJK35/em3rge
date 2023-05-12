/*
 * 
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Window extends JPanel {

    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;

    public static final int MENU_WIDTH = 200;

    public static final int DISPLAY_X = 200;
    public static final int DISPLAY_Y = 200;
    public static final int DISPLAY_WIDTH = 400;
    public static final int DISPLAY_HEIGHT = 400;

    


    public static void main(String[] args) {
        Window w = new Window();
    }

    public Window() {
        JFrame frame = new JFrame("em3rge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        Display d = new Display();
        frame.add(d);
        
        frame.setVisible(true);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                
            }
        });

        // d.getWorld().addParticle(new Particle(0, 11, 0));
        // d.getWorld().addParticle(new Particle(0, 10, 0));
        // d.getWorld().addParticle(new Particle(11, 1, 1, -1));
        
        // d.getWorld().addParticle(new Particle(10, 0, 0));
        // d.getWorld().addParticle(new Particle(10, 2, 0));
        // d.getWorld().addParticle(new Particle(12, 0, 0));
        // d.getWorld().addParticle(new Particle(12, 2, 0));
        // d.getWorld().addParticle(new Particle(10, 0, 2));
        // d.getWorld().addParticle(new Particle(10, 2, 2));
        // d.getWorld().addParticle(new Particle(12, 0, 2));
        // d.getWorld().addParticle(new Particle(12, 2, 2));

        World w = d.getWorld();
        double radius = 25;
        int pcount = 0;
        for (double p = Math.PI/2; p > -Math.PI/2; p -= Math.PI/12) {
            double pcos = Math.cos(p);
            double psin = Math.sin(p);
            for (double y = Math.PI; y > -Math.PI; y -= Math.PI/12) {
                double ycos = Math.cos(y);
                double ysin = Math.sin(y);

                double xOffset = radius * pcos * ycos;
                double yOffset = radius * pcos * ysin;
                double zOffset = radius * psin;
                w.addParticle(new Particle(
                    World.SIZE / 2 + xOffset,
                    World.SIZE / 2 + yOffset,
                    World.SIZE / 2 + zOffset
                ));
                pcount++;
            }
        }
        System.out.println("done: " + pcount);
        //System.out.println(d.getWorld().getParticles());

        

        d.renderParticles();
    }
}
