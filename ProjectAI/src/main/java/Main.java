import components.Factory;
import components.Store;
import components.Truck;
import environment.GridEnvir;
import fr.emse.fayol.maqit.simulator.components.Message;
import fr.emse.fayol.maqit.simulator.configuration.IniFile;
import fr.emse.fayol.maqit.simulator.configuration.SimProperties;
import fr.emse.fayol.maqit.simulator.environment.Cell;
import fr.emse.fayol.maqit.simulator.environment.GridEnvironment;
import fr.emse.fayol.maqit.simulator.environment.GridManagement;
import requests.MessageController;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int gridSize = 10;
        int maxProduction = 50;
        double maxStock = 100;
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

        GridManagement gridManagement = new GridManagement(99,10, 10,1);
        GridEnvir grid = new GridEnvir(sim, gridManagement);
        //GridEnvironment gridEnvironment = gridManagement.

        Factory factory = new Factory(1, gridManagement.getPlace(), new int[]{0, 255, 0}, maxProduction);
        Store[] stores = new Store[4];
        stores[0] = new Store(1, gridManagement.getPlace(), new int[]{255, 0, 0}, maxStock, factory, 4);
        stores[1] = new Store(2, gridManagement.getPlace(), new int[]{255, 0, 0}, maxStock, factory, 8);
        stores[2] = new Store(3, gridManagement.getPlace(), new int[]{255, 0, 0}, maxStock, factory, 12);
        stores[3] = new Store(4, gridManagement.getPlace(), new int[]{255, 0, 0}, maxStock, factory, 8);


        Truck truck = new Truck(1, 0, gridManagement.getPlace(), gridSize, gridSize);

        grid.createTurtlebot();

        for (int i = 0; i <4; i++) {
            grid.createObstacle(); // Blue color for stores
        }

        for (int i = 0; i < 1; i++) {
            grid.createObstacle(); // Yellow color for factory
        }

        int numInteractions = 5;
        int t = 0; // Number of iterations since the last delivery
        Random random = new Random();

        for (int i = 0; i < numInteractions; i++) {
            System.out.println("Interaction: " + (i + 1));

            // Calculate the demand for each store
            for (Store store : stores) {
                double ri = -1.5 + random.nextDouble() * 3; // Random number between [-1.5, 1.5]
                double DMi = 6.25 * Math.sin((2 * Math.PI * t / 12) + (i * Math.PI / 6)) + ri;
                double demand = DMi * maxStock / 100;
                store.sendPriorityMessage(factory, demand);
                Message message = new Message(store.getId(), Double.toString(store.calculatePriority(store.factoryDistance, demand))); // Create a new Message object with appropriate values
                factory.receivePriorityMessage(message);
            }



            // Get the store with the highest priority
            Store targetStore = null;
            double highestPriority = Double.MIN_VALUE;
            for (Store store : stores) {
                if (store.getStock() <= 0) {
                    continue;
                }
                double demand = store.calculatedDemand(i);
                double priority = store.calculatePriority(store.factoryDistance,demand);

                if (store.getStock() < maxStock * 0.2 || (demand * 100 / maxStock) > 30) {
                    // Emergency stock case
                    priority *= 1000;
                }

                if (priority > highestPriority) {
                    highestPriority = priority;
                    targetStore = store;
                }
            }

            if (targetStore != null) {
                //truck.moveTo(targetStore.getCell());
                t = 0; // Reset the number of iterations since the last delivery
            } else {
                t++; // Increment the number of iterations since the last delivery
            }
        }
    }
}

