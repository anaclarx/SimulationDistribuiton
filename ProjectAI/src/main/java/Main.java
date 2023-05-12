import environment.GridEnvir;
import fr.emse.fayol.maqit.simulator.configuration.IniFile;
import fr.emse.fayol.maqit.simulator.configuration.SimProperties;
import fr.emse.fayol.maqit.simulator.environment.ColorCell;
import fr.emse.fayol.maqit.simulator.environment.GridManagement;

public class Main {
    public static void main(String args[]) {
        IniFile config;
        try {
            config = new IniFile("configuration.ini");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SimProperties sim = new SimProperties(config);
        sim.initMQTT();
        sim.displayParams();
        sim.simulationParams();
        ;
        ColorCell.defaultcolor = new int[]{255, 255, 255};
        GridManagement gridManagement = new GridManagement(99, 10, 10, 1);
        GridEnvir grid = new GridEnvir(sim, gridManagement);
        //GridEnvir grid = new GridEnvir(sim, new GridManagement(99, 10, 10, "CustomSim", 0, 0, 500, 500, 1));
        // Create TurtleBots (trucks) and assign them to factories
        int numTrucks = 3;
        int numFactories = 1;
        int numStores = 4;

        for (int i = 0; i < numTrucks; i++) {
            grid.createTurtlebot(new int[]{0, 255, 0}); // Green color for TurtleBots
        }

        for (int i = 0; i < numStores; i++) {
            grid.createObstacle(new int[]{0, 0, 255}); // Blue color for stores
        }

        for (int i = 0; i < numFactories; i++) {
            grid.createObstacle(new int[]{255, 255, 0}); // Yellow color for factory
        }
        // Assign TurtleBots to factories
        for (int i = 0; i < numTrucks; i++) {
            int truckId = i + 1;
            int factoryId = i % numFactories + 1;
            gridManagement.addComponent(gridManagement.getPlace(), truckId);
            gridManagement.setGoal(gridManagement.getPlace(), factoryId);
        }

        // Assign stores to factories
        for (int i = 0; i < numStores; i++) {
            int storeId = i + 1;
            int factoryId = i % numFactories + 1;
            gridManagement.addComponent(gridManagement.getPlace(), storeId);
            gridManagement.setGoal(gridManagement.getPlace(), factoryId);
        }

        // Perform the simulation steps
        int numSteps = 10;
        for (int step = 0; step < numSteps; step++) {
            // Move trucks from factories to stores
            for (int i = 0; i < numTrucks; i++) {
                int truckId = i + 1;
                int factoryId = i % numFactories + 1;
                int storeId = i % numStores + 1;

                if (gridManagement.env.getCellContent(factoryId, truckId) == truckId) {
                    int[] truckPosition = gridManagement.getPlace();
                    int[] storePosition = gridManagement.getPlace();

                    gridManagement.moveComponent(truckPosition, storePosition, truckId);
                    gridManagement.setGoal(storePosition, storeId);
                }
            }

            grid.schedule();


        }
    }
}
