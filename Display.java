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
        world = new World();
        cam = new Camera();
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    public void initialize() {
        setFocusable(true);
        setEnabled(true);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point center = new Point(
                    (int) getLocationOnScreen().getX() + getWidth() / 2, 
                    (int) getLocationOnScreen().getY() + getHeight() / 2
                );

                robot.mouseMove((int) center.getX(), (int) center.getY());
                
                cam.getInfo()[Camera.ROTATION][Camera.PITCH] -= (e.getY() - getHeight() / 2) / 1000.0;
                cam.getInfo()[Camera.ROTATION][Camera.YAW] -= (e.getX() - getWidth() / 2) / 1000.0;
                //renderParticles();
            }
        });

    }

    public void renderParticles() {
        cam.update();
        Graphics g = getGraphics();
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
            if (particlePosition != null && (horizontalDisplay ? 
            Math.abs(particlePosition.getZ()) < displayRatio : 
            Math.abs(particlePosition.getX()) < displayRatio)) {
                rendered[i] = particlePosition;
            }
        }

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.fillOval(0, 0, 10, 10);
        for (int i = 0; i < rendered.length; i++) {
            RenderedParticle particlePosition = rendered[i];
            if (particlePosition != null) {
                g.setColor(Color.getHSBColor((float) particlePosition.getType() / Physics.getTypes(), 0.75f, 1.0f));
                int size = particlePosition.getRenderedSize();
                
                g.fillOval(
                    (int) (getWidth() / 2 + longDimension * particlePosition.getX() - size/2.0), 
                    (int) (getHeight() / 2 + longDimension * particlePosition.getZ() - size/2.0), 
                    size, size
                );
            }
        }
        //System.out.println(world.getParticles().size() + " particles at " + (System.currentTimeMillis() - start) + "ms/frame");
    }

    public KeyAdapter getKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_U) {
                    world.updateParticles();
                }

                if (e.getKeyCode() == KeyEvent.VK_C) {
                    cam.getInfo()[Camera.POSITION][Camera.X] = 0;
                    cam.getInfo()[Camera.POSITION][Camera.Y] = 50;
                    cam.getInfo()[Camera.POSITION][Camera.Z] = 50;
                    cam.getInfo()[Camera.ROTATION][Camera.PITCH] = 0;
                    cam.getInfo()[Camera.ROTATION][Camera.YAW] = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    for (int p = 0; p < 1000; p++) {
                        world.getParticles()[p] = new Particle();
                    }
                    world.setPhysics(new LinearPhysics(Physics.getTypes()));
                }

                if (e.getKeyCode() == KeyEvent.VK_W) {
                    cam.getInfo()[Camera.POSITION][Camera.X] += MOVE_SPEED * cam.getCosYaw();
                    cam.getInfo()[Camera.POSITION][Camera.Y] += MOVE_SPEED * cam.getSinYaw();
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    cam.getInfo()[Camera.POSITION][Camera.X] -= MOVE_SPEED * cam.getSinYaw();
                    cam.getInfo()[Camera.POSITION][Camera.Y] += MOVE_SPEED * cam.getCosYaw();
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    cam.getInfo()[Camera.POSITION][Camera.X] -= MOVE_SPEED * cam.getCosYaw();
                    cam.getInfo()[Camera.POSITION][Camera.Y] -= MOVE_SPEED * cam.getSinYaw();
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    cam.getInfo()[Camera.POSITION][Camera.X] += MOVE_SPEED * cam.getSinYaw();
                    cam.getInfo()[Camera.POSITION][Camera.Y] -= MOVE_SPEED * cam.getCosYaw();
                }

                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    cam.getInfo()[Camera.POSITION][Camera.Z] += 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    cam.getInfo()[Camera.POSITION][Camera.Z] -= 1;
                }

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

    public World getWorld() {
        return world;
    }

    public Camera getCamera() {
        return cam;
    }

    public void paint(Graphics g) {
        super.paint(g);
        renderParticles();
    }

    public void repaint(Graphics g) {
        super.repaint();
        renderParticles();
    }
}
