import java.awt.Point;

public class Camera {

    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
    public static final int YAW = 0;
    public static final int PITCH = 1;

    public static final double WIDTH = 4.0;
    public static final double HEIGHT = 3.0;
    public static final double FOCAL_LENGTH = 5.0;

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

    public Point renderParticle(Particle p) {
        double[] worldDist = p.distance(focusPoint);
        //rotate x and y by -yaw
        double newX = worldDist[X] * COS_YAW + worldDist[Y] * SIN_YAW;
        double newY = worldDist[Y] * COS_YAW - worldDist[X] * SIN_YAW;
        double screenX = FOCAL_LENGTH * newX / newY;

        //rotate xy and z by -pitch
        double xyDist = Math.sqrt(newY * newY + newX * newX);
        double newXY = xyDist * COS_PITCH + worldDist[Z] * SIN_PITCH;
        double newZ = worldDist[Z] * COS_PITCH - xyDist * SIN_PITCH;
        double screenZ = FOCAL_LENGTH * newXY / newZ;

        return new Point();
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
    }


}
