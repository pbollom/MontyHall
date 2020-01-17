package Home;

import Code.Simulation;

/**
 * Component for interacting with the business logic.
 */
public class Controller {
    public Controller() {

    }

    /**
     * Does the work of simulating the passed number of games
     * @param numberOfIterations Number of iterations to try.
     * @return
     */
    public SimulationResult Simulate(int numberOfIterations) {
        Simulation sim = new Simulation(numberOfIterations);
        sim.Simulate();

        SimulationResult result = new SimulationResult(sim.GetNumCorrectIfStayed(), sim.GetNumCorrectIfSwitched());

        return result;
    }
}
