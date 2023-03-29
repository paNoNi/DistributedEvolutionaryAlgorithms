package lab5;

import java.util.Random;

public interface CustomCrossoverInterface {
    double change(double[][] parents, int currentParent, int selectedPoint, Random random);
}