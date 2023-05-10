import java.awt.Point;
import java.util.Arrays;

public class Camera {

    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
    public static final int YAW = 0;
    public static final int PITCH = 1;
    public static final int POSITION = 0;
    public static final int ROTATION = 1;

    public static final double FOCAL_LENGTH = 0.5;
    public static final double WIDTH = FOCAL_LENGTH * 0.8;
    public static final double HEIGHT = FOCAL_LENGTH * 0.6;
    

    //camera info
    private double[] position; // X, Y, Z
    private double[] rotation; // Yaw, Pitch

    //calculated values for every camera angle
    private double SIN_YAW;
    private double COS_YAW;
    private double SIN_PITCH;
    private double COS_PITCH;
    private double[] focusPoint;


    public Camera() {

        position = new double[] {0.0, 0.0, 0.0};
        rotation = new double[] {0.0, 0.0};

        focusPoint = new double[3];

    }

    public double[][] getInfo() {
        return new double[][] {position, rotation};
    }

    public RenderedParticle renderParticle(Particle p) {
        //TODO properly modify relative position
        double[] worldDist = p.distance(position);

        //rotate by -yaw
        double newX = (worldDist[X] * COS_YAW + worldDist[Y] * SIN_YAW);
        double newY = (worldDist[Y] * COS_YAW - worldDist[X] * SIN_YAW);
        //rotate by -pitch
        double newZ = (worldDist[Z] * COS_PITCH - newX * SIN_PITCH);
        newX = (newX * COS_PITCH + worldDist[Z] * SIN_PITCH);
        
        if (p.getType() == -1) System.out.println(newX + " " + newY + " " + newZ);
        if (newX < 0) {
            return null;
        }

        double screenX = FOCAL_LENGTH * -newY / newX;
        double screenZ = FOCAL_LENGTH * -newZ / newX;

        if (p.getType() == -1) System.out.println(newX + " " + newY + " " + newZ);

        screenX += WIDTH * 0.5;
        screenZ += HEIGHT * 0.5;
        
        if (screenZ < 0 || screenZ > HEIGHT || screenX < 0 || screenX > WIDTH) {
            return null;
        }

        screenX /= WIDTH;
        screenZ /= HEIGHT;

        return new RenderedParticle(
            (int) (screenX * Window.DISPLAY_DIMENSION.getWidth()), 
            (int) (screenZ * Window.DISPLAY_DIMENSION.getHeight()),
            Math.sqrt(newX * newX + newY * newY + newZ * newZ)
        );
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
        }

        if (rotation[PITCH] > Math.PI / 2) {
            rotation[PITCH] = Math.PI / 2;
        }
        if (rotation[PITCH] < -Math.PI / 2) {
            rotation[PITCH] = -Math.PI / 2;
        }

        rotation[YAW] %= 2 * Math.PI;
        rotation[PITCH] %= 2 * Math.PI;

        calculateFocusPoint();
    }

    public double getCosYaw() {
        return COS_YAW;
    }
    public double getSinYaw() {
        return SIN_YAW;
    }

    public String toString() {
        return "(X: " + position[X] + ", Y: " + position[Y] + ", Z: " + position[Z] + 
        ", PITCH: " + rotation[PITCH] + ", YAW: " + rotation[YAW] + ")";
    }
}
