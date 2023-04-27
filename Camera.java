import java.awt.Point;

public class Camera {

    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
    public static final int YAW = 0;
    public static final int PITCH = 1;
    public static final int POSITION = 0;
    public static final int ROTATION = 1;

    public static final double WIDTH = 4.0;
    public static final double HEIGHT = 3.0;
    public static final double FOCAL_LENGTH = 5.0;

    //camera info
    private double[] position; // X, Y, Z
    private double[] rotation; // Yaw, Pitch

    //calculated values for every camera angle
    public double SIN_YAW;
    public double COS_YAW;
    public double SIN_PITCH;
    public double COS_PITCH;
    private double[] focusPoint;


    public Camera() {

        position = new double[] {0.0, 0.0, 0.0};
        rotation = new double[] {0.0, 0.0};

        focusPoint = new double[3];

    }

    public double[][] getInfo() {
        return new double[][] {position, rotation};
    }

    public Point renderParticle(Particle p) {
        double[] worldDist = p.distance(position);
        //rotate x and y by -yaw
        double newX = worldDist[X] * COS_YAW + worldDist[Y] * SIN_YAW;
        double newY = worldDist[Y] * COS_YAW - worldDist[X] * SIN_YAW;
        //TODO DONT RENDER PARTICLE
        newX = newX * COS_PITCH + worldDist[Z] * SIN_PITCH;
        newY = newY * COS_PITCH - worldDist[Z] * SIN_PITCH;
        

        //rotate x, y and z by -pitch
        double xyDist = Math.sqrt(newY * newY + newX * newX);
        //System.out.println(newXY);
        /*
        newX = x cos θ + z sin θ;
        newZ = −x sin θ + z cos θ;
        newY = y cos θ − z sin θ;
        newZ = y sin θ + z cos θ;
        */
        if (newY < 0) {
            //return new Point();
        }
        double newZ = worldDist[Z] * COS_PITCH + xyDist * SIN_PITCH;
        double screenY = FOCAL_LENGTH * newY / newX;
        double screenZ = FOCAL_LENGTH * newZ / newX;

        //System.out.println(newX + " " + newY + " " + newZ);

        screenY += WIDTH / 2;
        screenZ += HEIGHT / 2;

        screenY /= WIDTH;
        screenZ /= HEIGHT;

        return new Point(
            (int) (screenY * Window.DISPLAY_DIMENSION.getWidth()), 
            (int) (screenZ * Window.DISPLAY_DIMENSION.getHeight())
        );
    }

    public void calculateFocusPoint() {

        SIN_YAW = Math.sin(rotation[YAW]);
        COS_YAW = Math.cos(rotation[YAW]);
        SIN_PITCH = Math.sin(rotation[PITCH]);
        COS_PITCH = Math.cos(rotation[PITCH]);

        double xOffset = FOCAL_LENGTH * COS_PITCH * SIN_YAW;
        double yOffset = FOCAL_LENGTH * COS_PITCH * COS_YAW;
        double zOffset = FOCAL_LENGTH * SIN_PITCH;

        focusPoint[X] = position[X] - xOffset;
        focusPoint[Y] = position[Y] - yOffset;
        focusPoint[Z] = position[Z] - zOffset;

        focusPoint[X] %= World.size[X];
        focusPoint[Y] %= World.size[Y];
        focusPoint[Z] %= World.size[Z];
    }

    public void update() {
        position[X] %= World.size[X];
        position[Y] %= World.size[Y];
        position[Z] %= World.size[Z];

        if (rotation[PITCH] > Math.PI / 2) {
            rotation[PITCH] = Math.PI / 2;
        }
        if (rotation[PITCH] < -Math.PI / 2) {
            rotation[PITCH] = -Math.PI / 2;
        }

        while (rotation[YAW] > Math.PI) {
            rotation[YAW] -= Math.PI * 2;
        }
        while (rotation[YAW] < -Math.PI) {
            rotation[YAW] += Math.PI * 2;
        }

        calculateFocusPoint();
    }

    public String toString() {
        return "(X: " + position[X] + ", Y: " + position[Y] + ", Z: " + position[Z] + 
        ", PITCH: " + rotation[PITCH] + ", YAW: " + rotation[YAW] + ")";
    }
}
