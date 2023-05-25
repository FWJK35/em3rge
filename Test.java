import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Particle a = new Particle(75, 0, 0);
        System.out.println(Arrays.toString(a.distance(new double[] {0, 0, 0})));
    }
}