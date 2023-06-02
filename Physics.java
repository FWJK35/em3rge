/* 
 * Physics class determines calculations and rulesets for
 * interacting Particles within range. Acceleration follows
 * linear pattern, increasing from negative to positive within
 * certain bounds and then decreasing up to the edge of range
 */

import java.util.List;

public class Physics {
    private int types = 10;
    private double updateDistance = 30;
    private double repulsionTolerance = 1;
    private double forceScale = 1;
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
    public double getForceScale() {
        return forceScale;
    }
    public double getfriction() {
        return friction;
    }

    // mutators
    public void setUpdateDistance(double updateDistance) {
        this.updateDistance = updateDistance;
    }
    public void setRepulsionTolerance(double repulsionTolerance) {
        this.repulsionTolerance = repulsionTolerance;
    }
    public void setForceScale(double forceScale) {
        this.forceScale = forceScale;
    }
    public void setfriction(double friction) {
        this.friction = friction;
    }

    public void updateParticles(List<Particle> particles) {
        updateVelocity(particles);
        // decelerates velocity by multiplying friction every tick and updates position
        for (Particle p : particles) {
            for (int i = 0; i < World.dimensions; i++) {
                p.addPosition(i, p.velocityFriction(i, friction));
            }
        }
    }

    // approximates a few iterations' worth of updates by keeping same velocity
    public void updateParticles(List<Particle> particles, int iterations) {
        updateVelocity(particles);
        // decelerates velocity by multiplying friction every tick and updates position
        for (Particle p : particles) {
            for (int i = 0; i < World.dimensions; i++) {
                p.addPosition(i, iterations * p.velocityFriction(i, friction));
            }
        }
    }

    public double getForce(Particle a, Particle b, double realDist) {
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
            double scale = forceScale * (a.getPosition(i) - b.getPosition(i)) * distance;
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


}
