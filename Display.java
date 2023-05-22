import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

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
        setFocusable(true);
        setBounds(100, 100, 400, 400);
        
        addKeyListener(new KeyAdapter() {
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
                        world.getParticles().set(p, new Particle());
                    }
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
                //if (world.getParticles().size() < 1000) world.addParticle(new Particle());
                //renderParticles();
                //System.out.println(cam);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point center = new Point(
                    getLocationOnScreen().x + getWidth() / 2, 
                    getLocationOnScreen().y + getHeight() / 2
                );

                robot.mouseMove(center.x, center.y);
                setCursor(null);

                cam.getInfo()[Camera.ROTATION][Camera.PITCH] -= (e.getY() - getHeight() / 2) / 1000.0;
                cam.getInfo()[Camera.ROTATION][Camera.YAW] -= (e.getX() - getWidth() / 2) / 1000.0;
                //System.out.println(cam);
                //renderParticles();
            }
        });

    }

    public void renderParticles() {
        //long start = System.currentTimeMillis();
        //setBounds(100, 0, 400, 400);
        cam.update();
        Graphics g = getGraphics();
        Rectangle bounds = getBounds();

        
        
        boolean horizontalDisplay = bounds.getWidth() >= bounds.getHeight();
        double displayRatio = horizontalDisplay ? bounds.getHeight() / bounds.getWidth() : bounds.getWidth() / bounds.getHeight();
        double longDimension = (horizontalDisplay ?bounds.getWidth() : bounds.getHeight());

        //prerender particles
        ArrayList<Particle> particles = world.getParticles();
        RenderedParticle[] rendered = new RenderedParticle[particles.size()];
        for (int i = 0; i < particles.size(); i++) {
            Particle p = particles.get(i);
            RenderedParticle particlePosition = cam.renderParticle(p);
            rendered[i] = null;
            if (particlePosition != null && (horizontalDisplay ? 
            Math.abs(particlePosition.getZ()) < displayRatio : 
            Math.abs(particlePosition.getX()) < displayRatio)) {
                rendered[i] = particlePosition;
            }
        }

        g.setColor(Color.black);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        g.fillRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
        g.setColor(Color.white);
        for (int i = 0; i < rendered.length; i++) {
            RenderedParticle particlePosition = rendered[i];
            if (particlePosition != null) {
                int size = particlePosition.getRenderedSize();
                g.setColor(particlePosition.getColor());
                g.fillOval(
                    (int) (bounds.getCenterX() + longDimension * particlePosition.getX() - size/2.0), 
                    (int) (bounds.getCenterY() + longDimension * particlePosition.getZ() - size/2.0), 
                    size, size
                );
            }
        }
        //System.out.println(world.getParticles().size() + " particles at " + (System.currentTimeMillis() - start) + "ms/frame");
    }

    public World getWorld() {
        return world;
    }

    public Camera getCamera() {
        return cam;
    }

    public void paint(Graphics g) {
        
    }
}
