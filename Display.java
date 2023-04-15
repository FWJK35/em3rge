import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class Display extends JPanel {

    private World world;
    private Camera cam;
    private boolean show;

    public Display() {
        world = new World();
        cam = new Camera();
        show = false;
        setFocusable(true);
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    cam.getInfo()[Camera.POSITION][Camera.Y] += 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    cam.getInfo()[Camera.POSITION][Camera.X] -= 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    cam.getInfo()[Camera.POSITION][Camera.Y] -= 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    cam.getInfo()[Camera.POSITION][Camera.X] += 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    cam.getInfo()[Camera.POSITION][Camera.Z] += 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    cam.getInfo()[Camera.POSITION][Camera.Z] -= 1;
                }

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    cam.getInfo()[Camera.ROTATION][Camera.PITCH] += 0.01;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    cam.getInfo()[Camera.ROTATION][Camera.PITCH] -= 0.01;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    cam.getInfo()[Camera.ROTATION][Camera.YAW] -= 0.01;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    cam.getInfo()[Camera.ROTATION][Camera.YAW] += 0.01;
                }
                cam.update();
                System.out.println(cam.toString());
                renderParticles();
            }
        });
        
    }

    public void renderParticles() {
        Graphics g = getGraphics();
        g.clearRect(0, 0, 800, 600);
        for (Particle p : world.getParticles()) {
            
            Point particlePosition = cam.renderParticle(p);
            System.out.println(particlePosition);
            g.fillOval((int) particlePosition.getX(), (int) particlePosition.getY(), 5, 5);
        }
    }

    public World getWorld() {
        return world;
    }

    public void paint(Graphics g) {
        
    }
}
