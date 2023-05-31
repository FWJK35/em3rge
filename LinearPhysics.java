public class LinearPhysics extends Physics {
    private double[][] rule;

    // constructor
    public LinearPhysics(int types) {
        super();

        rule = new double[types][types];
        for (int i = 0; i < types; i++) {
            for (int j = 0; j < types; j++) {
                rule[i][j] = Math.random()*2 - 1;
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
    

    @Override
    public double getForce(Particle a, Particle b, double realDist) {
        double[] dist = a.distance(b);
        if (dist[0] < getUpdateDistance() && dist[1] < getUpdateDistance() && dist[2] < getUpdateDistance()) {
            realDist -= getRepulsionTolerance();
            if (realDist < getRepulsionTolerance()) {
                return realDist / getRepulsionTolerance();
            }
            else if (realDist < (getUpdateDistance() + getRepulsionTolerance()) * 0.5) {
                return realDist / (getUpdateDistance() - getRepulsionTolerance()) * getRule(a.getType(), b.getType());
            }
            else if (realDist < getUpdateDistance()) {
                return (1 - realDist / (getUpdateDistance() - getRepulsionTolerance())) * getRule(a.getType(), b.getType());
            }
        }
        return 0;
    }
}
