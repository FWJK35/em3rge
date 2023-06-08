/*
 *
 */

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class LinearRules extends JPanel {
    private World world;
    private int types;

    public LinearRules(World world) {
        //initialize variables
        this.world = world;
        this.types = world.getPhysics().getTypes();
        addMouseListener(getMouseAdapter());
    }

    public void paint(Graphics g) {
        //clear rules
        types = world.getPhysics().getTypes();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        int gridXSize = getWidth() / (types + 2);
        int gridYSize = getHeight() / (types + 2);
        int ovalSize = Math.min(gridXSize, gridYSize) / 2;
        for (int row = 0; row < types + 1; row++) {
            for (int col = 0; col < types + 1; col ++) {
                //fill in the rules
                if (row > 0 && col > 0) {
                    g.setColor(attractionToColor(world.getPhysics().getRules()[row - 1][col - 1]));
                    g.fillRect(col * gridXSize, row * gridYSize, gridXSize, gridYSize);
                    g.setColor(Color.WHITE);
                    g.drawRect(col * gridXSize, row * gridYSize, gridXSize, gridYSize);
                }
                //fill in the color labels horizontally
                else if (col > 0) {
                    g.setColor(Color.getHSBColor((float) (col - 1) / types, 0.75f, 1.0f));
                    g.fillOval(col * gridXSize + gridXSize / 4, gridYSize / 4, ovalSize , ovalSize);
                }
                //fill in the color labels vertically
                else if (row > 0) {
                    g.setColor(Color.getHSBColor((float) (row - 1) / types, 0.75f, 1.0f));
                    g.fillOval(gridXSize / 4, row * gridYSize + gridYSize / 4, ovalSize , ovalSize);
                }
            }
        }
    }

    public void repaint() {
        if (getGraphics() != null) {
            paint(getGraphics());
        }
        
    }

    //converts an attraction value to a color (red for repulsion green for attraction)
    private Color attractionToColor(double attraction) {
        if (attraction < 0) {
            return new Color(-(float) attraction, 0f, 0f);
        }
        else {
            return new Color(0f, (float) attraction, 0f);
        }
    }

    //gets the mouseadapter to register rule change clicks
    public MouseInputAdapter getMouseAdapter() {
        return new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //increase if left click, deccrease if right click
                double change = e.getButton() == MouseEvent.BUTTON1 ? 0.25 : 
                                (e.getButton() == MouseEvent.BUTTON2 ? 0 : 
                                (e.getButton() == MouseEvent.BUTTON3 ? -0.25 : 0));
                //get which grid cell was clicked
                int clickGridX = e.getX() * (types + 2) / getWidth();
                int clickGridY = e.getY() * (types + 2) / getHeight();
                //change that value
                if (clickGridX > 0 && clickGridX < types + 1 && clickGridY > 0 && clickGridY < types + 1) {
                    world.getPhysics().increment(clickGridY - 1, clickGridX - 1, change);
                    repaint();
                }
                //change whole row or column
                else if (clickGridX == 0 && clickGridY > 0 && clickGridY < types + 1) {
                    world.getPhysics().setRow(clickGridY - 1, change * 4);
                }
                else if (clickGridY == 0 && clickGridX > 0 && clickGridX < types + 1) {
                    world.getPhysics().setColumn(clickGridX - 1, change * 4);
                }
                
            }
        };
    }

    //gets the key adapter to register value changes
    public KeyAdapter getKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //generate new attraction matrix
                if (e.getKeyCode() == KeyEvent.VK_G) {
                    world.setPhysics(new Physics(world.getPhysics().getTypes()));
                }
                //clear attraction matrix
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    for (int r = 0; r < world.getPhysics().getRules().length; r++) {
                        for (int c = 0; c < world.getPhysics().getRules().length; c++) {
                            world.getPhysics().getRules()[r][c] = 0;
                        }
                    }
                }
                //generate snake attraction matrix
                if (e.getKeyCode() == KeyEvent.VK_Z) {
                    for (int r = 0; r < world.getPhysics().getRules().length; r++) {
                        for (int c = 0; c < world.getPhysics().getRules(r).length; c++) {
                            if (r == c) {
                                world.getPhysics().getRules()[r][c] = 1.0;
                            }
                            else if (r == (c + 1) % types) {
                                world.getPhysics().getRules()[r][c] = -1.0;
                            }
                            else if ((r + 1) % types == c) {
                                world.getPhysics().getRules()[r][c] = 0.5;
                            }
                            else {
                                world.getPhysics().getRules()[r][c] = 0.0;
                            }
                        }
                    }
                }
            }
        };
    }
}
