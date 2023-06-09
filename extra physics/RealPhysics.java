/*
 * RealPhysics class is a child class of Physics,
 * it uses the gravitational attraction equation
 * with a list of mass per particle type, and 
 * roughly approximates collisions (v. inaccurately)
 */

public class RealPhysics extends PhysicsBase {
    private double[] mass;

    // constructor
    public RealPhysics() {
        super();
    }

    public RealPhysics(int types) {
        super(types);
        //sets random masses
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
        
        double scale = 0;
        double[] direction = new double[3];

        // adjusting positions outside each other
        for (int i = 0; i < World.dimensions; i++) {
            double dist = .5 * a.distance(i, b);

            scale += dist * dist;
            direction[i] = dist;

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

        scale = 1 / scale;
        for (int i = 0; i < World.dimensions; i++) {
            a.addVelocity(i, direction[i] * scale);
        }
        
        return true;
    }

    //get force based on gravity
    @Override
    public double getForce(Particle a, Particle b, double realDist) {
        if (collision(a, b, realDist) || getUpdateDistance() < realDist) {
            return 0;
        }
        
        double force = 1 / realDist;
        force *= getForceScale() * mass[a.getType()] * mass[b.getType()] * force;
        return force;
    }
}
