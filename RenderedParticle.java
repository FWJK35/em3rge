public class RenderedParticle {

    private int screenX;
    private int screenZ;
    private double dist;

    public RenderedParticle(int screenX, int screenZ, double dist) {
        this.screenX = screenX;
        this.screenZ = screenZ;
        this.dist = dist;
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
}
