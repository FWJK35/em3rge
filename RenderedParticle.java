/*
 * 
 */

import java.awt.Color;

public class RenderedParticle {

    public static final double PARTICLE_SIZE = 50.0;

    private double screenX;
    private double screenZ;
    private double dist;
    private int renderedSize;
    private Color color;



    public RenderedParticle(double screenX, double screenZ, double dist, int type) {
        this.screenX = screenX;
        this.screenZ = screenZ;
        this.dist = dist;
        this.renderedSize = (int) (PARTICLE_SIZE * Camera.FOCAL_LENGTH / (Camera.FOCAL_LENGTH + dist));
        float scale = (float) type / Particle.getTypes();
        this.color = Color.getHSBColor(scale, 0.75f, 1.0f);
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

    public Color getColor() {
        return color;
    }

    public int getRenderedSize() {
        return renderedSize;
    }
}
