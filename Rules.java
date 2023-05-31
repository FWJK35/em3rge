import java.awt.*;

import javax.swing.JPanel;

public class Rules extends JPanel {
    private World world;
    private LinearPhysics physics;
    private int types;

    public Rules(World world) {
        this.world = world;
        this.physics = (LinearPhysics) world.getPhysics();
        this.types = physics.getTypes();
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        int gridXSize = getWidth() / (types + 2);
        int gridYSize = getHeight() / (types + 2);
        for (int row = 0; row < types + 1; row++) {
            for (int col = 0; col < types + 1; col ++) {
                if (row > 0 && col > 0) {
                    g.setColor(attractionToColor(physics.getRules()[row - 1][col - 1]));
                    g.fillRect(col * gridXSize, row * gridYSize, gridXSize, gridYSize);
                    g.setColor(Color.WHITE);
                    g.drawRect(col * gridXSize, row * gridYSize, gridXSize, gridYSize);
                }
            }
        }
    }

    private Color attractionToColor(double attraction) {
        if (attraction < 0) {
            return new Color(-(float) attraction, 0f, 0f);
        }
        else {
            return new Color(0f, (float) attraction, 0f);
        }
    }
}
