package components;

import fr.emse.fayol.maqit.simulator.components.ComponentType;
import fr.emse.fayol.maqit.simulator.components.Message;
import fr.emse.fayol.maqit.simulator.components.SituatedComponent;


public class Factory extends SituatedComponent {
    private int[] color;
    public double productionCapacity;
    private Store targetStore;
    private double stock;

    public Factory(int id, int[] location, int [] color, double productionCapacity) {
        super(id, location);
        this.color = color;
        this.productionCapacity = productionCapacity;
        this.targetStore = null;
        this.stock = 0.0;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.obstacle;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public double getProductionCapacity() {
        return productionCapacity;
    }

    public void setProductionCapacity(double productionCapacity) {
        this.productionCapacity = productionCapacity;
    }

    public Store getTargetStore() {
        return targetStore;
    }

    public void receivePriorityMessage(Message priorityMessage) {
        int storeId = priorityMessage.getEmitter();
        double priority = Double.parseDouble(priorityMessage.getContent());

        // Perform actions based on the received priority message
        // For example, calculate the production amount and set the target store
        double productionAmount = productionCapacity * priority;
        targetStore = findStoreById(storeId);

        // Update the stock
        stock -= productionAmount;
        if (stock < 0) {
            stock = 0;
        }
    }

    public void processRequests() {
        //already handled the processing of requests in `receivePriorityMessage()`
    }

    public double getStock() {
        return stock;
    }

    private Store findStoreById(int storeId) {
        return null;
    }
}
