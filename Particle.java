/*
 *
 */

public class Particle {
    public static final int RADIUS = 1;
    private static final double MAX_INITIAL_VELOCITY = 0;
    private double[] position;
    private double[] velocity;
    private int type;

    // constructors
    public Particle(int type, boolean random) {
        this.type = type;
        if (random) {
            this.type = (int) (Math.random() * type);
        }

        //randomly generate position and set velocity to 0
        this.position = new double[World.dimensions];
        this.velocity = new double[World.dimensions];
        for (int i = 0; i < World.dimensions; i++) {
            this.position[i] = World.SIZE * Math.random();
            this.velocity[i] = MAX_INITIAL_VELOCITY * Math.random() - .5 * MAX_INITIAL_VELOCITY;
        }
    }

    public Particle(double x, double y, double z, int type, boolean random) {
        this(type, random);
        this.position = new double[] {x, y, z};
    }

    //initializes particle with specific values and type
    public Particle(double x, double y, double z, double vx, double vy, double vz, int type, boolean random) {
        this(x, y, z, type, random);
        this.velocity = new double[] {vx, vy, vz};
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

    // mutator methods
    public double velocityFriction(int i, double friction) {
        velocity[i] *= 1 - friction;
        return velocity[i];
    }

    public void addPosition(int i, double addend) {
        position[i] = (position[i] + addend) % World.SIZE;

        if (position[i] < 0) {
            position[i] += World.SIZE;
        }
    }

    public void addVelocity(int i, double addend) {
        velocity[i] += addend;
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

    public double distance(int i, Particle p) {
        double di = position[i] - p.getPosition(i);
        double df = di + (di < 0 ? World.SIZE : -World.SIZE);

        return magMin(di, df);
    }

    // returns the minimum of two numbers by magnitude
    private static double magMin(double a, double b) {
        return Math.abs(a) < Math.abs(b) ? a : b;
    }

    public String toString() {
        return "{ Position: (X: " + position[0] + ", Y: " + position[1] + ", Z: " + position[2] + "), " + 
        "Velocity: (X: " + velocity[0] + ", Y: " + velocity[1] + ", Z: " + velocity[2] + ")";
    }
}