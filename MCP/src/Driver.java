/**
 * @author Aaron Ramsey (aar17)
 * @version 1.0
 */

/**
 * a class to create the drivers with a receipt and a vehicle
 */
public class Driver {
    private Receipt receipt;
    private Vehicle vehicle;

    /**
     * the empty constructor
     */
    public Driver(){}

    /**
     * the costructor that takes in a vehicle and a receipt
     * @param receipt the drivers receipt
     * @param vehicle the drivers vehicle
     */
    public Driver(Receipt receipt, Vehicle vehicle){
        this.receipt = receipt;
        this.vehicle = vehicle;
    }

    /**
     * gets the receipt
     * @return the receipt
     */
    public Receipt getReceipt() {
        return receipt;
    }

    /**
     * sets the receipt
     * @param receipt the receipt
     */
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    /**
     * gets the vehicle
     * @return the vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * sets the vehicle
     * @param vehicle the vehicle
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * the to string method
     * @return all the data in a string
     */
    @Override
    public String toString() {
        return "Driver{" +
                "receipt=" + receipt +
                ", vehicle=" + vehicle +
                '}';
    }
}
