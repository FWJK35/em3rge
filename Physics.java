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
        for (Particle p : particles) {
            System.out.println(p);
        }
        updateVelocity(particles);
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).updatePosition();
        }
        for (Particle p : particles) {
            System.out.println(p);
        }
    }

    public void updateVelocity(Particle a, Particle b) {
        for (int i = 0; i < World.dimensions; i++) {
            double scale = a.euclideanDistanceSquared(b);

            if (scale > Math.pow(Particle.RADIUS, 2)) {
                scale = (a.getPosition(i) - b.getPosition(i)) / scale;

                a.addVelocity(i, -scale * rule[a.getType()][b.getType()]);
                b.addVelocity(i, scale * rule[b.getType()][a.getType()]);
            }
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
