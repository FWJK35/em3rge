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
        setFocusable(true);
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_U) {
                    world.updateParticles();
                }

                if (e.getKeyCode() == KeyEvent.VK_R) {
                    cam.getInfo()[Camera.POSITION][Camera.X] = 1;
                    cam.getInfo()[Camera.POSITION][Camera.Y] = 0;
                    cam.getInfo()[Camera.POSITION][Camera.Z] = 0;
                    cam.getInfo()[Camera.ROTATION][Camera.PITCH] = Math.PI/2;
                    cam.getInfo()[Camera.ROTATION][Camera.YAW] = 0;
                }

                if (e.getKeyCode() == KeyEvent.VK_W) {
                    cam.getInfo()[Camera.POSITION][Camera.X] += MOVE_SPEED * cam.COS_YAW;
                    cam.getInfo()[Camera.POSITION][Camera.Y] += MOVE_SPEED * cam.SIN_YAW;
                    
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    cam.getInfo()[Camera.POSITION][Camera.X] -= MOVE_SPEED * cam.SIN_YAW;
                    cam.getInfo()[Camera.POSITION][Camera.Y] += MOVE_SPEED * cam.COS_YAW;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    cam.getInfo()[Camera.POSITION][Camera.X] -= MOVE_SPEED * cam.COS_YAW;
                    cam.getInfo()[Camera.POSITION][Camera.Y] -= MOVE_SPEED * cam.SIN_YAW;
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    cam.getInfo()[Camera.POSITION][Camera.X] += MOVE_SPEED * cam.SIN_YAW;
                    cam.getInfo()[Camera.POSITION][Camera.Y] -= MOVE_SPEED * cam.COS_YAW;
                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    cam.getInfo()[Camera.POSITION][Camera.Z] += 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    cam.getInfo()[Camera.POSITION][Camera.Z] -= 1;
                }

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    cam.getInfo()[Camera.ROTATION][Camera.PITCH] -= 0.01;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    cam.getInfo()[Camera.ROTATION][Camera.PITCH] += 0.01;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    cam.getInfo()[Camera.ROTATION][Camera.YAW] += 0.03;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    cam.getInfo()[Camera.ROTATION][Camera.YAW] -= 0.03;
                }
                //if (world.getParticles().size() < 1000) world.addParticle(new Particle());
                renderParticles();
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
                if (world.getParticles().size() < 1000) world.addParticle(new Particle());
                //System.out.println(cam);
                renderParticles();
            }
        });
    }

    public void renderParticles() {
        //long start = System.currentTimeMillis();
        cam.update();
        Graphics g = getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.white);
        for (int i = 0; i < world.getParticles().size(); i++) {
            Particle p = world.getParticles().get(i);
            RenderedParticle particlePosition = cam.renderParticle(p);
            if (particlePosition != null) {
                int size = (int) ((World.size[0] - particlePosition.getDist()) / 5);
                g.fillOval(particlePosition.getX(), particlePosition.getZ(), size, size);
                g.drawString(i + "", particlePosition.getX() + 10, particlePosition.getZ() - 10);
            }
            
            
        }
        //System.out.println(world.getParticles().size() + " particles at " + (System.currentTimeMillis() - start) + "ms/frame");
    }

    public World getWorld() {
        return world;
    }

    public void paint(Graphics g) {
        
    }
}
