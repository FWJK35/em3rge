/*
 *
 */

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class Settings extends JPanel {
    private World world;
    private Physics physics;

    public Settings(World world) {
        //initialize variables
        this.world = world;
        this.physics = this.world.getPhysics();
        addMouseListener(getMouseAdapter());
    }

    public void paint(Graphics g) {
        //clear rules
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        int textX = getWidth() / 4;
        g.drawString("Particle Count: " + world.getParticles().length, textX, 20);
        g.drawString("Type Count: " + physics.getTypes(), textX, 60);
        g.drawString("Force Scale: " + (int) ((physics.getForceScale()) * 1000000 + 0.5) / 1000000.0, textX, 100);
        g.drawString("Max Distance: " + physics.getUpdateDistance(), textX, 140);
        g.drawString("Repulsion Distance: " + physics.getRepulsionTolerance(), textX, 180);
        g.drawString("Friction: " + (int) ((physics.getFriction()) * 1000000 + 0.5) / 1000000.0, textX, 220);
        
        for (int b = 0; b < 6; b++) {
            g.drawRect(0, 40 * b, 40, 40);
            g.drawLine(10, 40 * b + 20, 30, 40 * b + 20);

            g.drawRect(getWidth() - 41, 40 * b, 40, 40);
            g.drawLine(getWidth() - 20, 40 * b + 10, getWidth() - 20, 40 * b + 30);
            g.drawLine(getWidth() - 10, 40 * b + 20, getWidth() - 30, 40 * b + 20);
        }
    }

    public void repaint() {
        if (getGraphics() != null) {
            paint(getGraphics());
        }
        
    }

    //gets the mouseadapter to register rule change clicks
    public MouseInputAdapter getMouseAdapter() {
        return new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //get which setting
                int clickGridRow = e.getY() / 40;
                int side = e.getX() < 40 ? -1 : (e.getX() > getWidth() - 40 ? 1 : 0);
                if (side != 0) {
                    //edit particle count
                    if (clickGridRow == 0) {
                        world.reset(world.getParticles().length + side * 100);
                    }
                    //edit type count
                    if (clickGridRow == 1) {
                        System.out.println(world.getPhysics());
                        world.setPhysics(new Physics(physics.getTypes() + side));
                        System.out.println(world.getPhysics());
                        world.reset();
                        physics = world.getPhysics();
                    }
                    //edit force scale
                    if (clickGridRow == 2) {
                        if (side == -1) {
                            physics.setForceScale(physics.getForceScale() / Math.pow(10, 0.25));
                        }
                        else {
                            physics.setForceScale(physics.getForceScale() * Math.pow(10, 0.25));
                        }
                    }
                    //edit update distance
                    if (clickGridRow == 3) {
                        physics.setUpdateDistance(physics.getUpdateDistance() + side * 2);
                        while (physics.getUpdateDistance() < physics.getRepulsionTolerance()) {
                            physics.setUpdateDistance(physics.getUpdateDistance() + 1);
                        }
                    }
                    //edit repulsion distance
                    if (clickGridRow == 4) {
                        physics.setRepulsionTolerance(physics.getRepulsionTolerance() + side);
                        while (physics.getRepulsionTolerance() > physics.getUpdateDistance()) {
                            physics.setRepulsionTolerance(physics.getRepulsionTolerance() - 1);
                        }
                    }
                    //edit friction
                    if (clickGridRow == 5) {
                        if (side == -1) {
                            physics.setFriction(physics.getFriction() / Math.pow(10, 0.25));
                        }
                        else {
                            physics.setFriction(physics.getFriction() * Math.pow(10, 0.25));
                        }
                    }
                }
                
            }
        };
    }

    //gets the key adapter to register value changes
    public KeyAdapter getKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                
            }
        };
    }
}

