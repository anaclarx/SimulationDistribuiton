package components;

import fr.emse.fayol.maqit.simulator.components.ComponentType;
import fr.emse.fayol.maqit.simulator.components.Message;
import fr.emse.fayol.maqit.simulator.components.SituatedComponent;

import java.util.Random;

public class Store extends SituatedComponent {
    private String color;
    private double currentStock;
    private double maxStock;
    private Factory factory;

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

    public String toString() {
        return super.toString() + ", color: " + this.color + ", currentStock: " + this.currentStock +
                ", maxStock: " + this.maxStock + ", factory: " + this.factory.getId();
    }

    public Store(int id, int[] location, String color, double maxStock, Factory factory) {
        super(id, location);
        this.color = color;
        this.maxStock = maxStock;
        this.currentStock = 0.0;
        this.factory = factory;
    }

    public void updateCurrentStock(double stockChang){
        this.currentStock += stockChang;
        if(this.currentStock < 0){
            this.currentStock = 0;
        }
        else if(this.currentStock > this.maxStock){
            this.currentStock = this.maxStock;
        }
    }

    public double calculatedDemand(double iteration){
        Random random = new Random();
        double ri = random.nextDouble() * 3 - 1.5;
        double i = iteration % 12 + 1;
        double dm = 6.25 * Math.sin((2* Math.PI/12) + (i-1) * Math.PI/6) + ri;
        return dm;
    }

    public double calculateImportance(double factoryDistance, double timeSinceLastDelivery){
        double i1 = (this.maxStock - this.currentStock) / this.maxStock;
        double i2 = Math.pow(calculatedDemand(timeSinceLastDelivery), timeSinceLastDelivery);
        double i3 = 1 / (1+ factoryDistance);
        double i4 = this.factory.productionCapacity / this.maxStock;
        return i1*i2*i3*i4;
    }

    public double calculatePriority(double factoryDistance, double timeSinceLastDelivery){
        double emergencyThresholdStockPercentage = 0.2;
        double emergencyThresholdDemandPercentage = 0.3;
        if(this.currentStock/this.maxStock< emergencyThresholdStockPercentage || (100 * calculatedDemand(timeSinceLastDelivery)/this.maxStock) > emergencyThresholdDemandPercentage){
            return Double.MAX_VALUE; // Highest Priority in emergency cases
        }
        else{
            double u = (this.currentStock / this.maxStock) + 100 * (1 - calculatedDemand(timeSinceLastDelivery)/this.maxStock);
            return u;
        }
    }

    public void sendPriorityMessage(Factory factory, double priority){
        Message priorityMessage = new Message(this.getId(), String.valueOf((priority)));
        factory.receivePriorityMessage(priorityMessage);
    }


}
