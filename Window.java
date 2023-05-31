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
        w.setVisible(true);
    }

    public Window() {
        super("em3rge");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(new GridBagLayout());
        setFocusable(true);

        //add display
        Display d = new Display();
        
        GridBagConstraints displayConstraints = new GridBagConstraints();
        displayConstraints.fill = GridBagConstraints.BOTH;
        displayConstraints.weightx = 1;
        displayConstraints.weighty = 1;
        displayConstraints.gridx = 1;
        displayConstraints.gridy = 0;
        displayConstraints.gridheight = 2;
        getContentPane().add(d, displayConstraints);
        d.initialize();
        addKeyListener(d.getKeyAdapter());
        d.requestFocusInWindow();

        //add attraction rules controls
        Rules rules = new Rules(d.getWorld());
        GridBagConstraints rulesConstraints = new GridBagConstraints();
        rulesConstraints.fill = GridBagConstraints.BOTH;
        rulesConstraints.weightx = 0.3;
        rulesConstraints.weighty = 0.5;
        rulesConstraints.gridx = 0;
        rulesConstraints.gridy = 0;
        getContentPane().add(rules, rulesConstraints);

        JButton controls = new JButton();
        controls.setSize(100, 100);
        GridBagConstraints controlsConstraints = new GridBagConstraints();
        controlsConstraints.fill = GridBagConstraints.BOTH;
        controlsConstraints.weightx = 0.3;
        controlsConstraints.weighty = 0.5;
        controlsConstraints.gridx = 0;
        controlsConstraints.gridy = 1;
        getContentPane().add(controls, controlsConstraints);

        World w = d.getWorld();
        /*double radius = 25;
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
        System.out.println("done: " + pcount); */

        for (int p = 0; p < 1000; p++) {
            w.addParticle(new Particle());
        }

        getRootPane().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyChar());
            }
        });
        
        new Timer(FRAME_LENGTH , new ActionListener() {

            public void actionPerformed(ActionEvent a) {
                w.updateParticles();
                d.renderParticles();
            }
        }).start();
    }
    
}
