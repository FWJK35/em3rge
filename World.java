/*
 * 
 */

import java.util.ArrayList;

public class World {

    public static final int dimensions = 3;
    public static final double SIZE = 100.0;

    private Physics physics;
    private ArrayList<Particle> particles;

    public World() {
        particles = new ArrayList<Particle>();
        physics = new RealPhysics(Particle.getTypes());
    }

    public Physics getPhysics() {
        return physics;
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void updateParticles() {
        physics.updateParticles(particles, 1);
    }

    public void addParticle(Particle p) {
        particles.add(p);
    }

}
