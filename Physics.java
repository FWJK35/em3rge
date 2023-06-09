/* 
 * Physics class determines calculations and rulesets for
 * interacting Particles within range. Updates velocity and
 * distances within an array using defined rules.
 */

public class Physics {
    private int types = 10;
    private double updateDistance = 50;
    private double repulsionTolerance = 2;
    private double repulsionScale = 50;
    private double forceScale = 0.0001;
    private double friction = .1;
    private double[][] rules;

    // constructors
    public Physics() {
        int types = getTypes();
        //generate random attraction matrix
        rules = new double[types][types];
        for (int i = 0; i < types; i++) {
            for (int j = 0; j < types; j++) {
                rules[i][j] = Math.random()*2 - 1;
            }
        }
    }

    public Physics(int types) {
        this.types = types;
        //generate random attraction matrix
        rules = new double[types][types];
        for (int i = 0; i < types; i++) {
            for (int j = 0; j < types; j++) {
                rules[i][j] = Math.random()*2 - 1;
            }
        }
    }

    // accessors
    public int getTypes() {
        return types;
    }
    public double getUpdateDistance() {
        return updateDistance;
    }
    public double getRepulsionTolerance() {
        return repulsionTolerance;
    }
    public double getRepulsionScale() {
        return repulsionScale;
    }
    public double getForceScale() {
        return forceScale;
    }
    public double getFriction() {
        return friction;
    }
    public double[][] getRules() {
        return rules;
    }
    public double[] getRules(int i) {
        return rules[i];
    }
    public double getRules(int i, int j) {
        return rules[i][j];
    }

    // mutators
    public void setTypes(int types) {
        this.types = types;
    }
    public void setUpdateDistance(double updateDistance) {
        this.updateDistance = updateDistance;
    }
    public void setRepulsionTolerance(double repulsionTolerance) {
        this.repulsionTolerance = repulsionTolerance;
    }
    public void setRepulsionScale(double repulsionScale) {
        this.repulsionScale = repulsionScale;
    }
    public void setForceScale(double forceScale) {
        this.forceScale = forceScale;
    }
    public void setFriction(double friction) {
        this.friction = friction;
    }
    public void increment(int i, int j, double step) {
        rules[i][j] += step;
        if (rules[i][j] > 1) {
            rules[i][j] = 1;
        }
        if (rules[i][j] < -1) {
            rules[i][j] = -1;
        }
    }
    //sets a row of attraction
    public void setRow(int row, double value) {
        for (int i = 0; i < rules[row].length; i++) {
            rules[row][i] = value;
        }
    }
    //sets a column of attraction
    public void setColumn(int column, double value) {
        for (int i = 0; i < rules.length; i++) {
            rules[i][column] = value;
        }
    }
    
    public void clone(Physics physics) {
        types = physics.getTypes();
        updateDistance = physics.getUpdateDistance();
        repulsionTolerance = physics.getRepulsionTolerance();
        repulsionScale = physics.getRepulsionScale();
        forceScale = physics.getForceScale();
        friction = physics.getFriction();
        rules = physics.getRules();
    }

    //updates velocity and position of particles based on force
    public void updateParticles(Particle[] particles) {
        updateVelocity(particles);
        // decelerates velocity by multiplying friction every tick and updates position
        for (Particle p : particles) {
            for (int i = 0; i < World.dimensions; i++) {
                p.addPosition(i, p.velocityFriction(i, friction));
            }
        }
    }

    // approximates a few iterations' worth of updates by keeping same velocity
    public void updateParticles(Particle[] particles, int iterations) {
        updateVelocity(particles);
        // decelerates velocity by multiplying friction every tick and updates position
        for (Particle p : particles) {
            for (int i = 0; i < World.dimensions; i++) {
                p.addPosition(i, iterations * p.velocityFriction(i, friction));
            }
        }
    }

    // updates the velocity due to the attraction/repulsion of two particles
    public void updateVelocity(Particle a, Particle b) {
        double realDist = Math.sqrt(a.euclideanDistanceSquared(b));

        // updates the velocity in each dimension based on the overall distance
        for (int i = 0; i < World.dimensions; i++) {
            double scale = forceScale * (a.distance(i, b));
            a.addVelocity(i, -scale * getForce(a, b, realDist));
            b.addVelocity(i, scale * getForce(b, a, realDist));
        }
    }

    // updates velocity of particles 
    public void updateVelocity(Particle[] particles) {
        for (int i = 0; i < particles.length - 1; i++) {
            for (int j = i + 1; j < particles.length; j++) {
                updateVelocity(particles[i], particles[j]);
            }
        }
    }

    public double getForce(Particle a, Particle b, double realDist) {
        double[] dist = a.distance(b);
        double updateDistance = getUpdateDistance(), repulsionTolerance = getRepulsionTolerance();
        double distance = realDist - repulsionTolerance;

        // repulses within repulsionTolerance 
        if (dist[0] < updateDistance && dist[1] < updateDistance && dist[2] < updateDistance) {
            if (distance < repulsionTolerance) {
                return getRepulsionScale() * distance / repulsionTolerance;
            }
            else if (distance < (updateDistance + repulsionTolerance) * 0.5) {
                return distance / (updateDistance - repulsionTolerance) * getRules(a.getType(), b.getType());
            }
            else if (distance < getUpdateDistance()) {
                return (1 - distance / (updateDistance - repulsionTolerance)) * getRules(a.getType(), b.getType());
            }
        }
        return 0;
    }
}
