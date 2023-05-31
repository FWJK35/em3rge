public class RealPhysics extends Physics {
    private double[] mass;

    // constructor
    public RealPhysics() {
        super();
    }

    public RealPhysics(int types) {
        super(types);

        mass = new double[types];
        for (int i = 0; i < types; i++) {
            mass[i] = Math.random();
        }
    }

    // accessors
    public double[] getMass() {
        return mass;
    }
    public double getMass(int i) {
        return mass[i];
    }

    // mutators
    public void setMass(int i, double mass) {
        this.mass[i] = mass; 
    }

    public boolean collision(Particle a, Particle b, double realDist) {
        if (realDist > Particle.RADIUS * 2) {
            return false;
        }
        
        // adjusting positions outside each other
        for (int i = 0; i < World.dimensions; i++) {
            double dist = .5 * a.distance(i, b);
            dist *= -1;
            if (dist < 0) {
                dist += Particle.RADIUS;
            }
            else {
                dist -= Particle.RADIUS;
            }
            a.addPosition(i, dist);
            b.addPosition(i, -dist);
        }
        // TODO: post-collision velocity changes
        return true;
    }

    @Override
    public double getForce(Particle a, Particle b, double realDist) {
        if (collision(a, b, realDist) || getUpdateDistance() < realDist) {
            return 0;
        }
        
        double force = 1/realDist;
        force *= getForceScale() * mass[a.getType()] * mass[b.getType()] * force;
        return force;
    }
}
