package lab5;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyCrossover extends AbstractCrossover<double[]> {


    CustomCrossoverInterface crossover;

    public MyCrossover(CustomCrossoverInterface crossover, int crossoverPoints) {
        super(crossoverPoints);
        this.crossover = crossover;
    }

    protected List<double[]> mate(double[] p1, double[] p2, int numberOfCrossoverPoints, Random random) {
        ArrayList<double[]> children = new ArrayList<double[]>();
        double[][] parents = new double[2][p1.length];
        parents[0] = p1;
        parents[1] = p2;
        for (int i = 0; i < 2; i++) {
            double[] child = parents[i].clone();
            for (int j = 0; j < numberOfCrossoverPoints; j++) {
                int selectedPoint = random.nextInt(p1.length);
                child[selectedPoint] = this.crossover.change(parents, i, selectedPoint, random);
            }
            children.add(child);
        }

        return children;
    }

}