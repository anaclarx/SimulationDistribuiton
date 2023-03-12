package components;

import fr.emse.fayol.maqit.simulator.SimFactory;
import fr.emse.fayol.maqit.simulator.configuration.SimProperties;
import fr.emse.fayol.maqit.simulator.environment.GridEnvironment;
import fr.emse.fayol.maqit.simulator.environment.GridManagement;

public class GridEnvir extends SimFactory {


    public GridEnvir(SimProperties sp, GridManagement env) {
        super(sp, env);
    }

    @Override
    public void createObstacle() {

    }

    @Override
    public void createObstacle(int[] ints) {

    }

    @Override
    public void createTurtlebot() {

    }

    @Override
    public void createTurtlebot(int[] ints) {

    }

    @Override
    public void schedule() {

    }
}
