import components.GridEnvir;
import fr.emse.fayol.maqit.simulator.configuration.IniFile;
import fr.emse.fayol.maqit.simulator.configuration.SimProperties;
import fr.emse.fayol.maqit.simulator.environment.ColorCell;
import fr.emse.fayol.maqit.simulator.environment.GridManagement;

public class Main {
    public static void main(String args[]){
        IniFile config;
        try{
            config = new IniFile("configuration.ini");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SimProperties sim = new SimProperties(config);
        sim.initMQTT();
        sim.displayParams();
        sim.simulationParams();;
        ColorCell.defaultcolor = new int[]{255,255,255};
        GridEnvir grid = new GridEnvir(sim, new GridManagement(99, 10, 10, "CustomSim", 0, 0, 500, 500, 1));
        grid.createObstacle();
        grid.createObstacle();
        grid.createTurtlebot();
        //grid.updateEnvironment(new int[]{5,5}, new int[]{2,2}, 1);

    }
}
