/*
 *
 */

public class Particle {

    public static final double UPDATE_DISTANCE = 50;

    private double[] position;
    private double[] velocity;
    private int type;

    // constructors
    public Particle() {
        //randomly generate position
        this.position = new double[World.dimensions];
        for (int i = 0; i < World.dimensions; i++) {
            this.position[i] = World.size[i] * Math.random();
        }
    }

    public Particle(double x, double y, double z) {
        //randomly generate position
        this.position = new double[] {x, y, z};
    }

    public Particle(int type) {
        this();
        this.type = type;
    }
    
    // accessor methods
    public double[] getPosition() {
        return position;
    }
    public double[] getVelocity() {
        return velocity;
    }
    public int getType() {
        return type;
    }

    public void update() {
        updatePosition();
        updateVelocity();
    }
    public void update(int ticks) {
        for (int i = 0; i < ticks; i++) {
            update();
        }
    }
    public void updatePosition() {
        for (int i = 0; i < World.dimensions; i++) {
            position[i] += velocity[i];
        }
    }
    public void updateVelocity() {
        
    }

    // returns the shortest displacement vector to a different 
    // point in the world, assuming the space extends onto itself
    public double[] distance(double[] pos) {
        double[] distance = new double[World.dimensions];
        for (int i = 0; i < World.dimensions; i++) {
            double di = pos[i] - position[i];
            double df = di + (di < 0 ? World.size[i] : -World.size[i]);
            distance[i] = magMin(di, df);
        }
        return distance;
    }

    public double[] distance(Particle p) {
        return distance(p.getPosition());
    }

    // returns the squared Euclidean distance to a different
    // Particle in the world, assuming the space extends onto itself
    public double euclideanDistanceSquared(Particle p) {
        double distance = 0;
        for (double di : distance(p)) {
            distance += di*di;
        }
        return distance;
    }

    // returns the minimum of two numbers by magnitude
    private static double magMin(double a, double b) {
        return Math.abs(a) < Math.abs(b) ? a : b;
    }

    public String toString() {
        return "(X: " + position[0] + ", Y: " + position[1] + ", Z: " + position[2] + ")";
    }
}