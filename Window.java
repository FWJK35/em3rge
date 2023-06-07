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

        //add attraction rules controls
        if (d.getWorld().getPhysics() instanceof LinearPhysics) {
        Rules rules = new Rules(d.getWorld());
        GridBagConstraints rulesConstraints = new GridBagConstraints();
        rulesConstraints.fill = GridBagConstraints.BOTH;
        rulesConstraints.weightx = 0.3;
        rulesConstraints.weighty = 0.5;
        rulesConstraints.gridx = 0;
        rulesConstraints.gridy = 0;
        getContentPane().add(rules, rulesConstraints);
        addKeyListener(rules.getKeyAdapter());
        }

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

        for (int p = 0; p < 1000; p++) {
            w.addParticle(new Particle());
        }

        getRootPane().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyChar());
            }
        });
        
        new Timer(FRAME_LENGTH, new ActionListener() {

            public void actionPerformed(ActionEvent a) {
                long st = System.currentTimeMillis();
                w.updateParticles();
                //System.out.println("Particles updated in " + (System.currentTimeMillis() - st) + " ms");
                st = System.currentTimeMillis();
                d.renderParticles();
                //System.out.println("Particles rendered in " + (System.currentTimeMillis() - st) + " ms");
            }

        }).start();
    }
    
}
