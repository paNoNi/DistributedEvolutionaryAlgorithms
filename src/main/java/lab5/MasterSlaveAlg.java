package lab5;

import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MasterSlaveAlg {

    public static void main(String[] args) {
        int dimension = 10; // dimension of problem
        int complexity = 5; // fitness estimation time multiplicator
        int populationSize = 1000; // size of population
        int generations = 300; // number of generations
        int crossoverPoints = 1;

        Random random = new Random(); // random

        CandidateFactory<double[]> factory = new MyFactory(dimension); // generation of solutions

        ArrayList<EvolutionaryOperator<double[]>> operators = new ArrayList<EvolutionaryOperator<double[]>>();
        operators.add(new MyCrossover(new ChooseCrossover(), crossoverPoints)); // Crossover
        operators.add(new MyCrossover(new MixCrossover(), crossoverPoints)); // Crossover
        operators.add(new MyMutation()); // Mutation
        EvolutionPipeline<double[]> pipeline = new EvolutionPipeline<double[]>(operators);

        SelectionStrategy<Object> selection = new RouletteWheelSelection(); // Selection operator

        FitnessEvaluator<double[]> evaluator = new MultiFitnessFunction(dimension, complexity); // Fitness function

        AbstractEvolutionEngine<double[]> algorithm = new SteadyStateEvolutionEngine<double[]>(
                factory, pipeline, evaluator, selection, populationSize, false, random);

        algorithm.setSingleThreaded(false);


        algorithm.addEvolutionObserver(new EvolutionObserver() {
            public void populationUpdate(PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
                System.out.println("Generation " + populationData.getGenerationNumber() + ": " + bestFit);
                System.out.println("\tBest solution = " + Arrays.toString((double[]) populationData.getBestCandidate()));
            }
        });

        TerminationCondition terminate = new GenerationCount(generations);
        long sTime = System.currentTimeMillis();
        algorithm.evolve(populationSize, 1, terminate);
        System.out.println("Time exicution: " + (System.currentTimeMillis() - sTime));

    }
}
