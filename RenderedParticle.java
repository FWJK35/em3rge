public class RenderedParticle {

    public static final double PARTICLE_SIZE = 50.0;

    private double screenX;
    private double screenZ;
    private double dist;
    private int renderedSize;
    private double renderedOpacity;



    public RenderedParticle(double screenX, double screenZ, double dist) {
        this.screenX = screenX;
        this.screenZ = screenZ;
        this.dist = dist;
        this.renderedSize = (int) (PARTICLE_SIZE * Camera.FOCAL_LENGTH / (Camera.FOCAL_LENGTH + dist));
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

    public int getRenderedSize() {
        return renderedSize;
    }
}
