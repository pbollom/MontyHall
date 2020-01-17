package Home;

/**
 * Class used by the UI to represent and pass around the results of a simulation.
 */
public class SimulationResult {
    private int NumCorrectIfStayed;
    private int NumCorrectIfSwitched;

    /**
     * Creates a new SimulationResult object
     * @param numCorrectIfStayed after the simulation, the number of iterations where the user picked the correct door originally
     * @param numCorrectIfSwitched after the simulation, the number of iterations where the user picked the correct door after switching
     */
    public SimulationResult(int numCorrectIfStayed, int numCorrectIfSwitched) {
        this.NumCorrectIfStayed = numCorrectIfStayed;
        this.NumCorrectIfSwitched = numCorrectIfSwitched;
    }

    /**
     * Returns the number of correct results if the user had stayed on their original door
     * @return number of correct results if the user had stayed on their original door
     */
    public int GetNumCorrectIfStayed() {
        return this.NumCorrectIfStayed;
    }

    /**
     * Returns the number of correct results if the user had switched from their original door
     * @return number of correct results if the user had switched from their original door
     */
    public int GetNumCorrectIfSwitched() {
        return this.NumCorrectIfSwitched;
    }
}
