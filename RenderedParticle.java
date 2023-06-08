/*
 * RenderedParticle.java holds the calculated position 
 * of Particles as projected on the screen / 2D plane. 
 */

import java.awt.Color;

public class RenderedParticle {

    public static final double PARTICLE_SIZE = 50.0;

    private double screenX;
    private double screenZ;
    private double dist;
    private int renderedSize;
    private int type;
    private Color color;



    public RenderedParticle(double screenX, double screenZ, double dist, int type) {
        this.screenX = screenX;
        this.screenZ = screenZ;
        this.dist = dist;
        //calculate size as rendered on screen
        this.renderedSize = (int) (PARTICLE_SIZE * Camera.FOCAL_LENGTH / (Camera.FOCAL_LENGTH + dist));
        this.type = type;
    }

    public double getX() {
        return screenX;
    }

    public double getZ() {
        return screenZ;
    }

    public double getDist() {
        return dist;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }

    public int getRenderedSize() {
        return renderedSize;
    }

    public int getType() {
        return type;
    }
}
