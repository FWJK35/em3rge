/*
 * The Window class holds the code for the frontend and UI
 * for the project, as well as controlling particle generation
 * and interaction speed. 
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Window extends JFrame {

    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;

    public static final int MENU_WIDTH = 200;

    public static final int DISPLAY_X = 200;
    public static final int DISPLAY_Y = 200;
    public static final int DISPLAY_WIDTH = 400;
    public static final int DISPLAY_HEIGHT = 400;

    public static final int FRAME_LENGTH = 10;


    public static void main(String[] args) {
        Window w = new Window();
    }

    public Window() {
        super("em3rge");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        Display d = new Display();
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        //d.setBounds(DISPLAY_X, DISPLAY_Y, DISPLAY_WIDTH, DISPLAY_HEIGHT);
        JButton b = new JButton();
        add(d, c);
        
        setVisible(true);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                
            }
        });

        World w = d.getWorld();
        // double radius = 25;
        // int pcount = 0;
        // for (double p = Math.PI/2; p > -Math.PI/2; p -= Math.PI/12) {
        //     double pcos = Math.cos(p);
        //     double psin = Math.sin(p);
        //     for (double y = Math.PI; y > -Math.PI; y -= Math.PI/12) {
        //         double ycos = Math.cos(y);
        //         double ysin = Math.sin(y);

        //         double xOffset = radius * pcos * ycos;
        //         double yOffset = radius * pcos * ysin;
        //         double zOffset = radius * psin;
        //         w.addParticle(new Particle(
        //             World.SIZE / 2 + xOffset,
        //             World.SIZE / 2 + yOffset,
        //             World.SIZE / 2 + zOffset
        //         ));
        //         pcount++;
        //     }
        // }
        // System.out.println("done: " + pcount);

        for (int p = 0; p < 1000; p++) {
            w.addParticle(new Particle());
        }

    
        d.renderParticles();
        while (true) { // keep running

            // update game logic based on time passed
            w.updateParticles();
            d.renderParticles();
            // update game logic once for every tick passed\
            int waitcount = 0;
            long startTime = System.currentTimeMillis() + FRAME_LENGTH;
            while (System.currentTimeMillis() < startTime) {
                System.out.println("waiting: " + waitcount);
                waitcount++;
                //System.out.println(d.getCamera());
                //d.renderParticles();
            }
            
        }
    }
}
