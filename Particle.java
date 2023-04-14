/*
 *
 */


public class Particle {
    private double[] position;
    private double[] velocity;
    private int type;

    // constructor
    public Particle() {
        //randomly generate position
        this.position = new double[World.dimensions];
        for (int i = 0; i < World.dimensions; i++) {
            this.position[i] = World.size[i] * Math.random();
        }
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

    // returns the shortest distance vector to a different 
    // Particle in the world, assuming the space extends onto itself
    public double[] distance(Particle p) {
        double[] distance = new double[World.dimensions];
        for (int i = 0; i < World.dimensions; i++) {
            double di = p.getPosition()[i] - position[i];
            distance[i] = di;
        }
        return distance;
    }

    public double[] distance(double[] pos) {
        double[] distance = new double[World.dimensions];
        for (int i = 0; i < World.dimensions; i++) {
            double di = pos[i] - position[i];
            double df = di + (di < 0 ? World.size[i] : -World.size[i]);
            distance[i] = magMin(di, df);
        }
        return distance;
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
}