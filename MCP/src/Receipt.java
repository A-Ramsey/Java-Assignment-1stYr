/**
 * @author Aaron Ramsey (aar17)
 * @version 1.0
 */

import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.System.currentTimeMillis;

/**
 * a class to create the receipts
 */
public class Receipt extends CodeTime {
    private float feeRate;
    private Zone zone;
    private Space space;

    /**
     * the empty constructor
     */
    public Receipt(){}

    /**
     * the constructor for reading in from files
     * @param feeRate the fee rate of the receipt
     * @param zone the zone they are parked in
     * @param space the space they are parked in
     * @param code the code to pay
     * @param parkTime the time they parked
     */
    public Receipt(float feeRate, Zone zone, Space space, int code, long parkTime){
        super(code, parkTime);
        this.feeRate = feeRate;
        this.zone = zone;
        this.space = space;
    }

    /**
     * the constructor to generate new receipts
     * @param code the code for the receipt
     * @param feeRate the fee rate of the receipt
     * @param zone the one they are parked in
     * @param space the space they are parked in
     */
    public Receipt(int code, float feeRate, Zone zone, Space space){
        super(code);
        this.feeRate = feeRate;
        this.zone = zone;
        this.space = space;
    }

    /**
     * gets the zone
     * @return the zone
     */
    public Zone getZone() {
        return zone;
    }

    /**
     * sets the zone
     * @param zone the zone
     */
    public void setZone(Zone zone) {
        this.zone = zone;
    }

    /**
     * gets the space they are parked in
     * @return the space
     */
    public Space getSpace() {
        return space;
    }

    /**
     * sets the space they are parked in
     * @param space the space
     */
    public void setSpace(Space space) {
        this.space = space;
    }

    /**
     * gets the name of the space
     * @return the id of the space
     */
    public int getSpaceName() {
        return space.getSpaceId();
    }

    /**
     * gets the name of the one
     * @return the id of the zone
     */
    public int getZoneName(){
        return zone.getId();
    }

    /**
     * method to pay the parking fee
     * @param scan the scanner object
     * @param mcp the car park
     * @throws IOException
     */
    public void pay(Scanner scan, CarPark mcp) throws IOException {
        long timeParked = currentTimeMillis() - super.getStartTime();
        long timeParkedHours = TimeUnit.MILLISECONDS.toHours(timeParked);
        float toPay = timeParkedHours*this.feeRate;

        System.out.print("Are you disabled?(y/n): ");
        if (scan.nextLine().toLowerCase().equals("y")){
            Calendar day = Calendar.getInstance();
            if (day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                toPay = mcp.getDisabledSunday();
            }
            else {
                toPay = toPay*mcp.getDisabledAdjust();
            }
        }

        toPay = Math.round(toPay);

        do {
            System.out.print("Please pay ");
            System.out.print(toPay);
            System.out.print(": ");
            Float inp = scan.nextFloat();
            if (inp < 0){
                System.out.println("You must pay a positive amount of money");
            } else {
                toPay = toPay - inp;
            }
        } while (toPay > 0);
        scan.nextLine();
        if (toPay < 0) {
            System.out.print("Change given: ");
            System.out.println(toPay*-1);
        }
    }

    /**
     * checks if the code of the receipt equals the one entered
     * @param that
     * @return
     */
    public boolean codeEquals(int that) {
        return (super.getCode() == that);
    }

    /**
     * outputs the data in the format for the text file
     * @return
     */
    public String toSave() {
        return feeRate + ":" + zone.getId() + ":" + space + ":" + super.toSave();
    }

    /**
     * the equals method
     * @param o object to compare with
     * @return whether the objects are equal or not
     */
    @Override
    public boolean equals(Object o) {
        Receipt receipt = (Receipt) o;
        return Objects.equals(super.getCode(), receipt.getCode());
    }

    /**
     * the to string method
     * @return the data in a string
     */
    @Override
    public String toString() {
        return "Receipt{" +
                "feeRate=" + feeRate +
                ", zone=" + zone +
                ", space=" + space +
                '}' + super.toString();
    }
}
