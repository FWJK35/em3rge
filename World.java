import java.util.ArrayList;

public class World {

    public static final int dimensions = 3;
    public static final double[] size = {1000.0, 1000.0, 1000.0};

    private ArrayList<Particle> particles;

    public World() {
        particles = new ArrayList<Particle>();
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void addParticle(Particle p) {
        particles.add(p);
    }

}
