package components;

import fr.emse.fayol.maqit.simulator.SimFactory;
import fr.emse.fayol.maqit.simulator.components.Obstacle;
import fr.emse.fayol.maqit.simulator.configuration.SimProperties;
import fr.emse.fayol.maqit.simulator.environment.GridEnvironment;
import fr.emse.fayol.maqit.simulator.environment.GridManagement;

public class GridEnvir extends SimFactory {

    private int idObs = 0;

    public GridEnvir(SimProperties sp, GridManagement env) {
        super(sp, env);
    }

    @Override
    public void createObstacle() {
        Obstacle obst = new Obstacle(idObs++, environment.getPlace());
        lobstacle.add(obst);
        addNewComponent(obst,new int[]{78,95,67});
    }

    @Override
    public void createObstacle(int[] ints) {
        Obstacle obst = new Obstacle(idObs++, ints);
        lobstacle.add(obst);
        addNewComponent(obst);
    }

    @Override
    public void createTurtlebot() {
        Robot bot = new Robot(1,1, environment.getPlace(), environment.getRows(), environment.getColumns());
        addNewComponent(bot, new int[]{24,30,200});
        bot.move(3);
        bot.moveForward();

    }

    @Override
    public void createTurtlebot(int[] ints) {

    }

    @Override
    public void schedule() {

    }
}

//como diferenciar os obstaculos entre si.
//como atualizar o grid/
