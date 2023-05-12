package requests;

import components.Factory;
import components.Truck;

public class MessageController {
    private Factory factory;
    private Truck truck;

    public MessageController(Factory factory, Truck truck){
        this.factory = factory;
        this.truck = truck;
    }

    public void prioritizeStores(){
        // Logic to prioritize stores based on the factory's internal state and send the priority message to the truck
        // You can implement this logic based on your specific requirements

        // Example implementation:
    }

}
