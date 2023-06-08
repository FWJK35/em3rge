/*
 * 
 */

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

public class Display extends JPanel {

    public static final double MOVE_SPEED = 1;

    private World world;
    private Camera cam;
    private Robot robot;

    public Display() {
        //create world and camera for rendering
        world = new World();
        cam = new Camera();

        //create robot for mouse movement
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    public void initialize() {

        //initialize component and mouse listener
        setFocusable(true);
        setEnabled(true);
        addMouseMotionListener(getMouseAdapter());

    }

    //render particles on screen using camera
    public void renderParticles(Graphics g) {
        cam.update();
        boolean horizontalDisplay = getWidth() >= getHeight();
        double displayRatio = horizontalDisplay ? (double) getHeight() / getWidth() : (double) getWidth() / getHeight();
        double longDimension = (horizontalDisplay ? getWidth() : getHeight());

        //prerender particles
        Particle[] particles = world.getParticles();
        RenderedParticle[] rendered = new RenderedParticle[particles.length];
        for (int i = 0; i < particles.length; i++) {
            Particle p = particles[i];
            RenderedParticle particlePosition = cam.renderParticle(p);
            rendered[i] = null;
            //check if particle needs to be rendered
            if (particlePosition != null && (horizontalDisplay ? 
            Math.abs(particlePosition.getZ()) < displayRatio : 
            Math.abs(particlePosition.getX()) < displayRatio)) {
                rendered[i] = particlePosition;
            }
        }

        //clear screen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        //render particles that should be visible
        for (int i = 0; i < rendered.length; i++) {
            RenderedParticle particlePosition = rendered[i];
            if (particlePosition != null) {
                //set color based on type
                g.setColor(Color.getHSBColor((float) particlePosition.getType() / world.getPhysics().getTypes(), 0.75f, 1.0f));
                int size = particlePosition.getRenderedSize();
                
                //draw the particle
                g.fillOval(
                    (int) (getWidth() / 2 + longDimension * particlePosition.getX() - size/2.0), 
                    (int) (getHeight() / 2 + longDimension * particlePosition.getZ() - size/2.0), 
                    size, size
                );
            }
        }
    }

    //method to get key adapter to be implemented into the window
    public KeyAdapter getKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                //reset particle positions random
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    for (int p = 0; p < 1000; p++) {
                        world.getParticles()[p] = new Particle(world.getPhysics().getTypes(), true);
                    }
                }
                //reset particle positions snake
                if (e.getKeyCode() == KeyEvent.VK_X) {
                    for (int p = 0; p < world.getParticles().length; p++) {
                        world.getParticles()[p] = new Particle(p * World.SIZE / world.getParticles().length, 
                        1 * Math.random(), 1 * Math.random(), (int) (((double) p / world.getParticles().length) * world.getPhysics().getTypes()), false);
                        
                    }
                }
                
                //move forward
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    cam.getInfo()[Camera.POSITION][Camera.X] += MOVE_SPEED * cam.getCosYaw();
                    cam.getInfo()[Camera.POSITION][Camera.Y] += MOVE_SPEED * cam.getSinYaw();
                }
                //move left
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    cam.getInfo()[Camera.POSITION][Camera.X] -= MOVE_SPEED * cam.getSinYaw();
                    cam.getInfo()[Camera.POSITION][Camera.Y] += MOVE_SPEED * cam.getCosYaw();
                }
                //move backward
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    cam.getInfo()[Camera.POSITION][Camera.X] -= MOVE_SPEED * cam.getCosYaw();
                    cam.getInfo()[Camera.POSITION][Camera.Y] -= MOVE_SPEED * cam.getSinYaw();
                }
                //move right
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    cam.getInfo()[Camera.POSITION][Camera.X] += MOVE_SPEED * cam.getSinYaw();
                    cam.getInfo()[Camera.POSITION][Camera.Y] -= MOVE_SPEED * cam.getCosYaw();
                }
                //move up
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    cam.getInfo()[Camera.POSITION][Camera.Z] += 1;
                }
                //move down
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    cam.getInfo()[Camera.POSITION][Camera.Z] -= 1;
                }
                //move camera with arrow keys
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    cam.getInfo()[Camera.ROTATION][Camera.PITCH] += Math.PI/100;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    cam.getInfo()[Camera.ROTATION][Camera.PITCH] -=Math.PI/100;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    cam.getInfo()[Camera.ROTATION][Camera.YAW] += Math.PI/100;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    cam.getInfo()[Camera.ROTATION][Camera.YAW] -= Math.PI/100;
                }
            }
        };
    }

    //method to get mouse adapter for camera movement
    public MouseMotionAdapter getMouseAdapter() {
        return new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point center = new Point(
                    (int) getLocationOnScreen().getX() + getWidth() / 2, 
                    (int) getLocationOnScreen().getY() + getHeight() / 2
                );
                //move mouse back to center of screen
                robot.mouseMove((int) center.getX(), (int) center.getY());
                //rotate camera
                cam.getInfo()[Camera.ROTATION][Camera.PITCH] -= (e.getY() - getHeight() / 2) / 1000.0;
                cam.getInfo()[Camera.ROTATION][Camera.YAW] -= (e.getX() - getWidth() / 2) / 1000.0;
            }
        };
    }

    public World getWorld() {
        return world;
    }

    public Camera getCamera() {
        return cam;
    }

    public void paint(Graphics g) {
        super.paint(g);
        renderParticles(g);
    }

    public void repaint(Graphics g) {
        super.repaint();
        renderParticles(g);
    }
}
