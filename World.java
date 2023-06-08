/*
 * The World class holds an array of Particles as
 * well as a Physics class used to update the array. 
 * It is representative of the world 
 */


public class World {

    public static final int dimensions = 3;
    public static final double SIZE = 100.0;

    private Physics physics;
    private Particle[] particles;
    //private int particleCount;

    public World() {
        //uses an array over arrayList for faster accessing
        this.particles = new Particle[0];
        this.physics = new Physics();
    }
    public World(int particles) {
        this.particles = new Particle[particles];
        this.physics = new Physics();
        for (int i = 0; i < particles; i++) {
            this.particles[i] = new Particle(physics.getTypes(), true);
        }
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
        physics.updateParticles(particles);
    }

    public void updateParticles(int iterations) {
        physics.updateParticles(particles, iterations);
    }

    //adds a particle to the array
    public void addParticle(Particle p) {
        Particle[] newParticles = new Particle[particles.length + 1];
        for (int i = 0; i < particles.length; i++) {
            newParticles[i] = particles[i];
        }
        newParticles[particles.length] = p;
        particles = newParticles;
    }

    public void reset() {
        for (int i = 0; i < particles.length; i++) {
            particles[i] = new Particle(physics.getTypes(), true);
        }
    }
    public void reset(int particles) {
        this.particles = new Particle[particles];
        for (int i = 0; i < particles; i++) {
            this.particles[i] = new Particle(physics.getTypes(), true);
        }
    }

}
