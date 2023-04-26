import java.util.List;

public class Physics {
    private final double step = .5;
    private double[][] rule;
    
    public Physics(int types) {
        rule = new double[types][types];
        for (int i = 0; i < types; i++) {
            for (int j = 0; j < types; j++) {
                rule[i][j] = 1;
            }
        }
    }

    public void updateParticles(List<Particle> particles) {
        updateVelocity(particles);
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).updatePosition();
        }
    }

    public void updateVelocity(Particle a, Particle b) {
        for (int i = 0; i < World.dimensions; i++) {
            double distSquared = Math.pow(a.getPosition()[i] - b.getPosition()[i], 2);
            
            a.addVelocity(i, rule[a.getType()][b.getType()] / distSquared);
            b.addVelocity(i, rule[b.getType()][a.getType()] / distSquared);
        }
    }
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
    public void increment(int i, int j) {
        rule[i][j] += step;
    }

    public void decrement(int i, int j) {
        rule[i][j] -= step;
    }    
}
