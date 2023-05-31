public class LinearPhysics extends Physics {
    private double[][] rules;

    // constructor
    public LinearPhysics() {
        super();
    }

    public LinearPhysics(int types) {
        super(types);

        rules = new double[types][types];
        for (int i = 0; i < types; i++) {
            for (int j = 0; j < types; j++) {
                rules[i][j] = Math.random()*2 - 1;
            }
        }
    }
    
    // accessors
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
    public void increment(int i, int j, double step) {
        rules[i][j] += step;
    }

    public void decrement(int i, int j, double step) {
        rules[i][j] -= step;
    }    
    

    @Override
    public double getForce(Particle a, Particle b, double realDist) {
        double[] dist = a.distance(b);
        if (dist[0] < getUpdateDistance() && dist[1] < getUpdateDistance() && dist[2] < getUpdateDistance()) {
            realDist -= getRepulsionTolerance();
            if (realDist < getRepulsionTolerance()) {
                return realDist / getRepulsionTolerance();
            }
            else if (realDist < (getUpdateDistance() + getRepulsionTolerance()) * 0.5) {
                return realDist / (getUpdateDistance() - getRepulsionTolerance()) * getRules(a.getType(), b.getType());
            }
            else if (realDist < getUpdateDistance()) {
                return (1 - realDist / (getUpdateDistance() - getRepulsionTolerance())) * getRules(a.getType(), b.getType());
            }
        }
        return 0;
    }
}
