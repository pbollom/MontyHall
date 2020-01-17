package Code;

import java.util.Random;

/**
 * Business logic for running multiple Monty Hall gameshow simulations
 */
public class Simulation {
    private int NumberOfIterations;

    private int NumCorrectIfStayed;
    private int NumCorrectIfSwitched;

    private Random RNG;

    /**
     *
     * @param numberOfIterations how many games to run
     */
    public Simulation(int numberOfIterations) {
        this.Reset(numberOfIterations);
    }

    //make this reusable in case we want to allow an external caller to reuse the same simulation.
    private void Reset(int numberOfIterations) {
        this.NumberOfIterations = numberOfIterations;
        this.NumCorrectIfStayed = 0;
        this.NumCorrectIfSwitched = 0;
        this.RNG = new Random();
    }

    /**
     * Does the simulation based on the parameter passed in the constructor.
     */
    public void Simulate() {
        for (int i = 0; i < this.NumberOfIterations; i++) {
            int doorWithPrize = this.RNG.nextInt(3);
            int doorPicked = this.RNG.nextInt(3);

            if (doorPicked == doorWithPrize) { //user chose to stay
                this.NumCorrectIfStayed++;
            }
            else { //user chose to switch after seeing the door that didn't have the prize
                this.NumCorrectIfSwitched++;
            }
        }
    }

    /**
     * After calling Simulate, this returns the number of games where the user would have been correct if they stayed
     * @return
     */
    public int GetNumCorrectIfStayed() {
        return this.NumCorrectIfStayed;
    }

    /**
     * After calling Simulate, this returns the number of games where the user would have been correct if they changed doors
     * @return
     */
    public int GetNumCorrectIfSwitched() {
        return this.NumCorrectIfSwitched;
    }
}
