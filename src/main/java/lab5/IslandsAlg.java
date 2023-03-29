package lab5;

import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.islands.IslandEvolution;
import org.uncommons.watchmaker.framework.islands.IslandEvolutionObserver;
import org.uncommons.watchmaker.framework.islands.Migration;
import org.uncommons.watchmaker.framework.islands.RingMigration;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class IslandsAlg {

    public static void main(String[] args) {
        int dimension = 10; // dimension of problem
        int complexity = 5; // fitness estimation time multiplicator
        int populationSize = 1000; // size of population
        int generations = 300; // number of generations
        int crossoverPoints = 1;
        int islandCounts = 5;
        int epochLength = 50;

        Random random = new Random(); // random

        CandidateFactory<double[]> factory = new MyFactory(dimension); // generation of solutions

        ArrayList<EvolutionaryOperator<double[]>> operators = new ArrayList<EvolutionaryOperator<double[]>>();
        operators.add(new MyCrossover(new ChooseCrossover(), crossoverPoints)); // Crossover
        operators.add(new MyCrossover(new MixCrossover(), crossoverPoints));
        operators.add(new MyMutation()); // Mutation
        EvolutionPipeline<double[]> pipeline = new EvolutionPipeline<double[]>(operators);

        SelectionStrategy<Object> selection = new RouletteWheelSelection(); // Selection operator

        FitnessEvaluator<double[]> evaluator = new MultiFitnessFunction(dimension, complexity); // Fitness function

        RingMigration migration = new RingMigration();
        IslandEvolution<double[]> island_model = new IslandEvolution<double[]>(
                islandCounts, migration, factory, pipeline, evaluator, selection, random
        ); // your model;

        island_model.addEvolutionObserver(new IslandEvolutionObserver() {
            public void populationUpdate(PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
                System.out.println("Epoch " + populationData.getGenerationNumber() + ": " + bestFit);
                System.out.println("\tEpoch best solution = " + Arrays.toString((double[])populationData.getBestCandidate()));
            }

            public void islandPopulationUpdate(int i, PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
                System.out.println("Island " + i);
                System.out.println("\tGeneration " + populationData.getGenerationNumber() + ": " + bestFit);
                System.out.println("\tBest solution = " + Arrays.toString((double[])populationData.getBestCandidate()));
            }
        });

        TerminationCondition terminate = new GenerationCount(generations / epochLength);
        long sTime = System.currentTimeMillis();
        island_model.evolve(populationSize / islandCounts, 1, epochLength, 2, terminate);
        System.out.println("Time exicution: " + (System.currentTimeMillis() - sTime));
    }
}
