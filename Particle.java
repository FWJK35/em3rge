/*
 *
 */

public class Particle {
    public static final int RADIUS = 2;
    private static final double FRICTION = .1;

    private double[] position;
    private double[] velocity;
    private int type;

    // constructors
    public Particle() {
        type = 0;

        //randomly generate position and set velocity to 0
        this.position = new double[World.dimensions];
        this.velocity = new double[World.dimensions];
        for (int i = 0; i < World.dimensions; i++) {
            this.position[i] = World.SIZE * Math.random();
            this.velocity[i] = 0;
        }
    }

    public Particle(double x, double y, double z, int type) {
        this(x, y, z);
        this.type = type;
    }

    public Particle(double x, double y, double z) {
        this();
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
    public double getPosition(int i) {
        return position[i];
    }

    public double[] getVelocity() {
        return velocity;
    }
    public double getVelocity(int i) {
        return velocity[i];
    }

    public int getType() {
        return type;
    }

    public void addVelocity(int i, double addend) {
        velocity[i] += addend;
    }

    public void updatePosition() {
        for (int i = 0; i < World.dimensions; i++) {
            velocity[i] *= 1 - FRICTION;
            position[i] = (position[i] + velocity[i]) % World.SIZE;

            if (position[i] < 0) {
                position[i] += World.SIZE;
            }
        }
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

    // returns the shortest displacement vector to a different 
    // point in the world, assuming the space extends onto itself
    public double[] distance(double[] pos) {
        double[] distance = new double[World.dimensions];
        for (int i = 0; i < World.dimensions; i++) {
            double di = position[i] - pos[i];
            double df = di + (di < 0 ? World.SIZE : -World.SIZE);
            distance[i] = magMin(di, df);
        }
        return distance;
    }

    public double[] distance(Particle p) {
        return distance(p.getPosition());
    }

    // returns the minimum of two numbers by magnitude
    private static double magMin(double a, double b) {
        return Math.abs(a) < Math.abs(b) ? a : b;
    }

    public String toString() {
        return "{ Position: (X: " + position[0] + ", Y: " + position[1] + ", Z: " + position[2] + "), Velocity: (X: " + velocity[0] + ", Y: " + velocity[1] + ", Z: " + velocity[2] + ")";
    }
}