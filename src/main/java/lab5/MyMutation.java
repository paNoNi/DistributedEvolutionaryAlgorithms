package lab5;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.List;
import java.util.Random;

public class MyMutation implements EvolutionaryOperator<double[]> {

    public List<double[]> apply(List<double[]> population, Random random) {
        int instDim = population.get(0).length;
        for (int i = 0; i < population.size(); i++) {
            int randomPoint = random.nextInt(instDim);
            double randomStep = random.nextDouble() * 5.0 - 2.5;
            double newValue = population.get(i)[randomPoint];
            if (newValue + randomStep > 5.0 | newValue + randomStep < -5.0) {
                randomStep = -randomStep;
            }
            newValue += randomStep;

            double[] instance = population.get(i);
            instance[randomPoint] = newValue;
            population.set(i, instance);
        }
        return population;
    }
}