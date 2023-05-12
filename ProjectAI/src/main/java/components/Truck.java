package components;

import fr.emse.fayol.maqit.simulator.robot.GridTurtlebot;

public class Truck extends GridTurtlebot {

    public static int idBot = 0;

    private TruckState truckstate;

    public Truck(int field, int debug, int[] pos, int r, int c) {
        super(idBot++, "components.Robot" + idBot, field, debug, pos, r, c);
        truckstate = TruckState.Free;
    }

    @Override
    public void move(int i) {
        int mov = 0;
        while(grid[0][1] == 0 && mov<i){
            this.moveForward();
            this.updatePerception(this.getPerception());
        }
    }

    public int row(){return this.rows;}

    public void free(){
        truckstate = TruckState.Free;
    }

}
