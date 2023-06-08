/*
 * The Window class holds the code for the frontend and UI
 * for the project, as well as controlling particle generation
 * and interaction speed. 
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Window extends JFrame {

    //static variables
    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;
    public static final int FRAME_LENGTH = 10;


    public static void main(String[] args) {
        Window w = new Window();
        w.setVisible(true);
    }

    public Window() {
        //create window
        super("em3rge");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(new GridBagLayout());
        setFocusable(true);

        //add display
        Display d = new Display();
        
        //set display size in window
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
        LinearRules rules = new LinearRules(d.getWorld());

        //set rules size in window
        GridBagConstraints rulesConstraints = new GridBagConstraints();
        rulesConstraints.fill = GridBagConstraints.BOTH;
        rulesConstraints.weightx = 0.3;
        rulesConstraints.weighty = 0.5;
        rulesConstraints.gridx = 0;
        rulesConstraints.gridy = 0;
        getContentPane().add(rules, rulesConstraints);
        addKeyListener(rules.getKeyAdapter());

        //add settings menu
        Settings settings = new Settings(d.getWorld());

        //set settings size in window
        GridBagConstraints controlsConstraints = new GridBagConstraints();
        controlsConstraints.fill = GridBagConstraints.BOTH;
        controlsConstraints.weightx = 0.3;
        controlsConstraints.weighty = 0.5;
        controlsConstraints.gridx = 0;
        controlsConstraints.gridy = 1;
        getContentPane().add(settings, controlsConstraints);

        //get world and add particles
        World w = d.getWorld();

        w.reset(1000);
        
        //begin frame update timer
        new Timer(FRAME_LENGTH, new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                try {
                    w.updateParticles();
                    repaint();
                }
                catch (Exception e) {

                }
            }
        }).start();
    }
    
}
