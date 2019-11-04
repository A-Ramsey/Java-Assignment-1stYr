/**
 * @author Aaron Ramsey (aar17)
 * @version 1.0
 */

import java.util.concurrent.TimeUnit;
import static java.lang.System.currentTimeMillis;

/**
 * a class for the exit tokens used to get out of the barrier
 */
public class ExitToken extends CodeTime{
    private int time2Leave;

    /**
     * the empty constructor
     */
    public ExitToken() {
    }

    /**
     * the constructor to generate new receipts
     * @param code code of the receipt
     * @param time2Leave the time they have till they have to leave
     */
    public ExitToken(int code, int time2Leave) {
        super(code);
        this.time2Leave = time2Leave;
    }

    /**
     * the constructor that takes all the parameters for reading from files
     * @param code code to add to the token
     * @param time2Leave the time they have to leave
     * @param tokenTime the time the token was generated
     */
    public ExitToken(int code, int time2Leave, long tokenTime) {
        super(code, tokenTime);
        this.time2Leave = time2Leave;
    }

    /**
     * the seek assistance method
     */
    private void seekAssitance() {
        System.err.println("Seek assistance");
    }

    /**
     * the method to leave the car park
     */
    public void leave(){
        long timeElapsed = currentTimeMillis() - super.getStartTime();
        long timeElapsedMins = TimeUnit.MILLISECONDS.toMinutes(timeElapsed);
        if (timeElapsedMins > time2Leave) {
            seekAssitance();
        }
    }
}
