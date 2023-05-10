public class RenderedParticle {

    public static final double PARTICLE_SIZE = 20.0;

    private int screenX;
    private int screenZ;
    private double dist;
    private int renderedSize;
    private double renderedOpacity;



    public RenderedParticle(int screenX, int screenZ, double dist) {
        this.screenX = screenX;
        this.screenZ = screenZ;
        this.dist = dist;
        this.renderedSize = (int) ((World.SIZE/2 - dist) / 2.5);

    }

    public int getX() {
        return screenX;
    }

    public int getZ() {
        return screenZ;
    }

    public double getDist() {
        return dist;
    }

    public int getRenderedSize() {
        return renderedSize;
    }
}
