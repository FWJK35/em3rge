import java.util.List;

public class Physics {
    private final double UPDATE_DISTANCE = 20;
    private final double REPULSION_TOLERANCE = 10;
    private final double FORCE_SCALE = 0.001;
    private final double FRICTION = .1;
    private double[][] rule;
    
    public Physics(int types) {
        rule = new double[types][types];
        for (int i = 0; i < types; i++) {
            for (int j = 0; j < types; j++) {
                rule[i][j] = .25;
            }
        }
    }

    public void updateParticles(List<Particle> particles) {
        updateVelocity(particles);
        // decelerates velocity by multiplying FRICTION every tick and updates position
        for (Particle p : particles) {
            for (int i = 0; i < World.dimensions; i++) {
                p.addPosition(i, p.velocityFriction(i, FRICTION));
            }
        }
    }

    public double getForce(Particle a, Particle b, double realDist) {
        double[] dist = a.distance(b);
        if (dist[0] < UPDATE_DISTANCE && dist[1] < UPDATE_DISTANCE && dist[2] < UPDATE_DISTANCE) {
            realDist -= REPULSION_TOLERANCE;
            if (realDist < REPULSION_TOLERANCE) {
                return realDist / REPULSION_TOLERANCE;
            }
            else if (realDist < (UPDATE_DISTANCE + REPULSION_TOLERANCE) * 0.5) {
                return realDist / (UPDATE_DISTANCE - REPULSION_TOLERANCE) * rule[a.getType()][b.getType()];
            }
            else if (realDist < UPDATE_DISTANCE) {
                return (1 - realDist / (UPDATE_DISTANCE - REPULSION_TOLERANCE)) * rule[a.getType()][b.getType()];
            }
        }
        return 0;
    }

    // updates the velocity due to the attraction/repulsion of two particles
    public void updateVelocity(Particle a, Particle b) {
        double realDist = Math.sqrt(a.euclideanDistanceSquared(b));
        double distance = 1;
        if (realDist != 0) {
            distance = 1 / realDist;
        }

        // updates the velocity in each dimension based on the overall distance
        for (int i = 0; i < World.dimensions; i++) {
            double scale = FORCE_SCALE * (a.getPosition(i) - b.getPosition(i)) * distance;
            a.addVelocity(i, -scale * getForce(a, b, realDist));
            b.addVelocity(i, scale * getForce(b, a, realDist));
        }
    }

    // updates velocity of particles 
    public void updateVelocity(List<Particle> particles) {
        for (int i = 0; i < particles.size() - 1; i++) {
            for (int j = i + 1; j < particles.size(); j++) {
                updateVelocity(particles.get(i), particles.get(j));
            }
        }
    }

    // accessors
    public double[][] getRule() {
        return rule;
    }
    public double[] getRule(int i) {
        return rule[i];
    }
    public double getRule(int i, int j) {
        return rule[i][j];
    }

    // mutators
    public void increment(int i, int j, double step) {
        rule[i][j] += step;
    }

    public void decrement(int i, int j, double step) {
        rule[i][j] -= step;
    }    
}
