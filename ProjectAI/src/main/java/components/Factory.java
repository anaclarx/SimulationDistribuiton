package components;

import fr.emse.fayol.maqit.simulator.components.ComponentType;
import fr.emse.fayol.maqit.simulator.components.Message;
import fr.emse.fayol.maqit.simulator.components.SituatedComponent;

public class Factory extends SituatedComponent {
    private String color;
    public double productionCapacity;

    public Factory(int id, int[] location, String color, double productionCapacity) {
        super(id, location);
        this.color = color;
        this.productionCapacity = productionCapacity;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.obstacle;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void receivePriorityMessage(Message priorityMessage){
        int storeId = priorityMessage.getEmitter();
        double priority = Double.parseDouble(priorityMessage.getContent());
    }

}
