import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class LinearRules extends JPanel {
    private World world;
    private LinearPhysics physics;
    private int types;

    public LinearRules(World world) {
        this.world = world;
        this.physics = (LinearPhysics) this.world.getPhysics();
        this.types = Physics.getTypes();
        addMouseListener(getMouseAdapter());
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

    public MouseInputAdapter getMouseAdapter() {
        return new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                double change = e.getButton() == MouseEvent.BUTTON1 ? 0.25 : (e.getButton() == MouseEvent.BUTTON3 ? -0.25 : 0);
                int clickGridX = e.getX() * (types + 2) / getWidth();
                int clickGridY = e.getY() * (types + 2) / getHeight();
                if (clickGridX > 0 && clickGridX < types + 1 && clickGridY > 0 && clickGridY < types + 1) {
                    System.out.println(clickGridX + " " + clickGridY);
                    physics.increment(clickGridY - 1, clickGridX - 1, change);
                    System.out.println(change);
                    System.out.println(physics.getRules()[0][0]);
                    repaint();
                }
                
            }
        };
    }

    public KeyAdapter getKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    //world.setPhysics(new LinearPhysics(Physics.getTypes()));
                    physics = (LinearPhysics) world.getPhysics();
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_G) {
                    world.setPhysics(new LinearPhysics(Physics.getTypes()));
                    physics = (LinearPhysics) world.getPhysics();
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    for (int r = 0; r < physics.getRules().length; r++) {
                        for (int c = 0; c < physics.getRules(r).length; c++) {
                            physics.getRules()[r][c] = 0;
                        }
                    }
                    physics = (LinearPhysics) world.getPhysics();
                    repaint();
                }
            }
        };
    }
}
