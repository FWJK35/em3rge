/*
 * The Camera class represents the point and orientation in space that
 * the user views from. It renders Particles by projecting their position
 * and scaling their size onto a 2D viewframe, the screen.  
 */

public class Camera {

    //math constants
    private static final double halfPi = Math.PI * 0.5;
    private static final double root3 = Math.sqrt(3);
    private static final double root3inverse = 1/Math.sqrt(3);
    
    // indices for values within position and rotation arrays
    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
    public static final int YAW = 0;
    public static final int PITCH = 1;
    public static final int POSITION = 0;
    public static final int ROTATION = 1;

    public static final double FOCAL_LENGTH = 5;
    public static final double WIDTH = FOCAL_LENGTH * root3;
    public static final double HEIGHT = FOCAL_LENGTH * root3;
    public static final double RENDER_DISTANCE = World.SIZE / root3;
    

    //camera info
    enum RenderType {Inside, Outside;}
    private RenderType renderType = RenderType.Inside;

    private double[] position; // X, Y, Z
    private double[] rotation; // Yaw, Pitch

    //calculated values for every camera angle
    private double SIN_YAW;
    private double COS_YAW;
    private double SIN_PITCH;
    private double COS_PITCH;
    private double[] focusPoint;

    // constructor
    public Camera() {

        position = new double[] {0.0, 0.0, 0.0};
        rotation = new double[] {0.0, 0.0};

        focusPoint = new double[3];

    }

    // accessors
    public double[][] getInfo() {
        return new double[][] {position, rotation};
    }
    public double getCosYaw() {
        return COS_YAW;
    }
    public double getSinYaw() {
        return SIN_YAW;
    }
    public RenderType getRenderType() {
        return renderType;
    }

    public RenderedParticle renderParticle(Particle p) {
        //TODO properly modify relative position
        double[] worldDist = p.distance(position);
        //System.out.println(Arrays.toString(p.getPosition()) + Arrays.toString(position) + Arrays.toString(worldDist));

        //rotate by -yaw
        double newX = (worldDist[X] * COS_YAW + worldDist[Y] * SIN_YAW);
        double newY = (worldDist[Y] * COS_YAW - worldDist[X] * SIN_YAW);
        //rotate by -pitch
        double newZ = (worldDist[Z] * COS_PITCH - newX * SIN_PITCH);
        newX = (newX * COS_PITCH + worldDist[Z] * SIN_PITCH);
        double newXInverse = 1 / newX;
        if (newX < 0) {
            return null;
        }

        double screenX = root3inverse * -newY * newXInverse;
        double screenZ = root3inverse * -newZ * newXInverse;
        
        if (Math.abs(screenZ) > 1 || Math.abs(screenX) > 1) {
            return null;
        }

        return new RenderedParticle(screenX, screenZ, Math.sqrt(newX * newX + newY * newY + newZ * newZ), p.getType());
    }

    public void calculateFocusPoint() {

        SIN_YAW = Math.sin(rotation[YAW]);
        COS_YAW = Math.cos(rotation[YAW]);
        SIN_PITCH = Math.sin(rotation[PITCH]);
        COS_PITCH = Math.cos(rotation[PITCH]);

        double xOffset = FOCAL_LENGTH * COS_PITCH * COS_YAW;
        double yOffset = FOCAL_LENGTH * COS_PITCH * SIN_YAW;
        double zOffset = FOCAL_LENGTH * SIN_PITCH;

        focusPoint[X] = position[X] - xOffset;
        focusPoint[Y] = position[Y] - yOffset;
        focusPoint[Z] = position[Z] - zOffset;

        for (int i = 0; i < World.dimensions; i++) {
            focusPoint[i] %= World.SIZE;
        }
    }

    public void update() {
        for (int i = 0; i < World.dimensions; i++) {
            position[i] %= World.SIZE;
            if (position[i] < 0) position[i] += World.SIZE;
        }

        // if (rotation[PITCH] > Math.PI / 2) {
        //     rotation[PITCH] = Math.PI / 2;
        // }
        // if (rotation[PITCH] < -Math.PI / 2) {
        //     rotation[PITCH] = -Math.PI / 2;
        // }

        rotation[YAW] %= 2 * Math.PI;
        if (Math.abs(rotation[PITCH]) > halfPi) {
            rotation[PITCH] = Math.signum(rotation[PITCH]) * halfPi;
        }

        calculateFocusPoint();
    }

    public String toString() {
        return "(X: " + position[X] + ", Y: " + position[Y] + ", Z: " + position[Z] + 
        ", PITCH: " + rotation[PITCH] + ", YAW: " + rotation[YAW] + ")";
    }
}
