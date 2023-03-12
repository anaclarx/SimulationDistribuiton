package components;

import fr.emse.fayol.maqit.simulator.SimFactory;
import fr.emse.fayol.maqit.simulator.robot.GridTurtlebot;

public class Robot extends GridTurtlebot {

    public static int idBot = 0;

    public Robot(int field, int debug, int[] pos, int r, int c) {
        super(idBot++, "components.Robot" + idBot, field, debug, pos, r, c);
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

}
