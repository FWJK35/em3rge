import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class Rules extends JPanel {
    private World world;
    private LinearPhysics physics;
    private int types;

    public Rules(World world) {
        this.world = world;
        this.physics = (LinearPhysics) this.world.getPhysics();
        this.types = Physics.getTypes();
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
                else if (col > 0) {
                    g.setColor(Color.getHSBColor((float) (col - 1) / types, 0.75f, 1.0f));
                    g.fillOval(col * gridXSize + gridXSize / 4, gridYSize / 4, gridXSize / 2 , gridYSize / 2);
                }
                else if (row > 0) {
                    g.setColor(Color.getHSBColor((float) (row - 1) / types, 0.75f, 1.0f));
                    g.fillOval(gridXSize / 4, row * gridYSize + gridYSize / 4, gridXSize / 2 , gridYSize / 2);
                }
            }
        }
    }

    public KeyAdapter getKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    physics =(LinearPhysics) world.getPhysics();
                    repaint();
                }
            }
        };
    }

    public void repaint() {
        if (getGraphics() != null) {
            paint(getGraphics());
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
