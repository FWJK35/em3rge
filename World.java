/*
 * 
 */

public class World {

    public static final int dimensions = 3;
    public static final double SIZE = 100.0;

    private Physics physics;
    private Particle[] particles;
    //private int particleCount;

    public World() {
        //particleCount = 1000;
        particles = new Particle[0];
        physics = new LinearPhysics(Physics.getTypes());
    }

    // accessors
    public Physics getPhysics() {
        return physics;
    }

    public Particle[] getParticles() {
        return particles;
    }

    // mutators
    public void setPhysics(Physics physics) {
        this.physics = physics;
    }

    public void updateParticles() {
        physics.updateParticles(particles, 1);
    }

    public void addParticle(Particle p) {
        Particle[] newParticles = new Particle[particles.length + 1];
        for (int i = 0; i < particles.length; i++) {
            newParticles[i] = particles[i];
        }
        newParticles[particles.length] = p;
        particles = newParticles;
    }

}
