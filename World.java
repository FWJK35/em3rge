import java.util.ArrayList;

public class World {

    public static final int dimensions = 3;
    public static final double[] size = {100.0, 100.0, 100.0};

    private Physics physics;
    private ArrayList<Particle> particles;

    public World() {
        particles = new ArrayList<Particle>();
        physics = new Physics(1);
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void updateParticles() {
        physics.updateParticles(particles);
    }

    public void addParticle(Particle p) {
        particles.add(p);
    }

}
