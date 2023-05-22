package environment;

import components.Truck;
import fr.emse.fayol.maqit.simulator.SimFactory;
import fr.emse.fayol.maqit.simulator.components.Obstacle;
import fr.emse.fayol.maqit.simulator.components.Turtlebot;
import fr.emse.fayol.maqit.simulator.configuration.SimProperties;
import fr.emse.fayol.maqit.simulator.environment.GridManagement;
import fr.emse.fayol.maqit.simulator.robot.GridTurtlebot;

public class GridEnvir extends SimFactory {

    private int idObs = 0;

    public String obsType = new String();

    public GridEnvir(SimProperties sp, GridManagement env) {
        super(sp, env);
    }

    @Override
    public void createObstacle() {
        //Obstacle obst = new Obstacle(idObs++, environment.getPlace());
        Obstacle obst = new Obstacle(idObs++, environment.getPlace());
        lobstacle.add(obst);
        addNewComponent(obst);
    }

    @Override
    public void createObstacle(int[] ints) {
        Obstacle obst = new Obstacle(idObs++, ints);
        lobstacle.add(obst);
        addNewComponent(obst, ints);
    }

    @Override
    public void createTurtlebot() {
        Truck truck = new Truck(1,1, environment.getPlace(), environment.getRows(), environment.getColumns());
        truck.randomOrientation();
        lrobot.add(truck);
        addNewComponent(truck);
    }

    @Override
    public void createTurtlebot(int[] ints) {
        Truck truck = new Truck(1,1, environment.getPlace(), environment.getRows(), environment.getColumns());
        truck.randomOrientation();
        lrobot.add(truck);
        addNewComponent(truck, ints);
    }

    @Override
    public void schedule() {
        int numBots = lrobot.size();
        for(Turtlebot robot : lrobot){
            if (robot instanceof GridTurtlebot gRobot) {
                gRobot.updatePerception(gRobot.getPerception());
            }
            int[] oldCell = robot.getLocation();
            //robot.move(200);
            robot.moveForward();
            environment.moveComponent(oldCell, robot.getLocation(), robot.getId());
            updateEnvironment(oldCell, robot.getLocation(), robot.getId());
        }
    }
}

//como diferenciar os obstaculos entre si.
//como atualizar o grid/
//o que é a propriedade seed?
