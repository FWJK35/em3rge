public class RealPhysics extends Physics {
    private double[] mass;

    // constructor
    public RealPhysics(int types) {
        super();

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
        return true;
    }

    @Override
    public double getForce(Particle a, Particle b, double realDist) {
        if (collision(a, b, realDist)) {
            return 0;
        }
        
        return 0;
    }
}
