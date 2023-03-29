package lab5;

import java.util.Random;

public class ChooseCrossover implements CustomCrossoverInterface {


    public double change(double[][] parents, int currentParent, int selectedPoint, Random random) {
        if (currentParent == 0) {
            return parents[1][selectedPoint];
        } else {
            return parents[0][selectedPoint];
        }
    }
}