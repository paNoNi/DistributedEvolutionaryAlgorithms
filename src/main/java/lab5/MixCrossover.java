package lab5;

import java.util.Random;

public class MixCrossover implements CustomCrossoverInterface {


    public double change(double[][] parents, int currentParent, int selectedPoint, Random random) {
        double high = Math.max(parents[0][selectedPoint], (parents[1][selectedPoint]));
        double low = Math.min(parents[0][selectedPoint], (parents[1][selectedPoint]));
        return random.nextDouble() * (high - low) + low;
    }
}