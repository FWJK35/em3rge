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
        double updateDistance = getUpdateDistance(), repulsionTolerance = getRepulsionTolerance();
        double distance = realDist - repulsionTolerance;
        if (dist[0] < updateDistance && dist[1] < updateDistance && dist[2] < updateDistance) {
            if (distance < repulsionTolerance) {
                return distance / repulsionTolerance;
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
