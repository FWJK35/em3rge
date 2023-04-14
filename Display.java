import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Display extends JPanel {

    ArrayList<Particle> particles;

    //X, Y, Z, Pitch, Yaw
    double[] camera;

    public Display (ArrayList<Particle> particles) {
        this.particles = particles;
    }

    public void paint(Graphics g) {

    }
}
