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

    // constructor
    public Physics() {
    }

    public Physics(int types) {
        this.types = types;
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
    public void clone(Physics physics) {
        types = physics.getTypes();
        updateDistance = physics.getUpdateDistance();
        repulsionTolerance = physics.getRepulsionTolerance();
        repulsionScale = physics.getRepulsionScale();
        forceScale = physics.getForceScale();
        friction = physics.getFriction();
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
    
    //overwritten in child class
    public double getForce(Particle a, Particle b, double realDist) {
        return 0;
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


}
