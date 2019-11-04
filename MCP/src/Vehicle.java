/**
 * @author Aaron Ramsey (aar17)
 * @version 1.0
 */

import java.util.Objects;

/**
 * a class to create the vehicles
 */
public class Vehicle {
    private String licencePlate;
    private Size size;

    /**
     * the empty constructor
     */
    public Vehicle(){}

    /**
     * the constructor to generate a vehicle
     * @param licence the licence plate of the vehicle
     * @param s the size of the vehicle
     */
    public Vehicle(String licence, Size s){
        this.licencePlate = licence;
        this.size = s;
    }

    /**
     * parks a vehicle
     * @param space space to park the vehicle in
     */
    public void park(Space space){
        space.setOccupiedBy(this);
    }

    /**
     * unparks a vehicle (i.e. empties space)
     * @param space space to unpark a car from
     */
    public void unpark(Space space){
        space.setOccupiedBy(null);
    }

    /**
     * gets the licence plate
     * @return the licence plate
     */
    public String getLicencePlate() {
        return licencePlate;
    }

    /**
     * the size of the vehicle
     * @return
     */
    public Size getSize() {
        return size;
    }

    /**
     * outputs the data in a form to save to text files
     * @return
     */
    public String toSave() {
        return licencePlate + ":" + size;
    }

    /**
     * the to string method
     * @return outputs the data in a string
     */
    @Override
    public String toString() {
        return "Vehicle{" +
                "licencePlate='" + licencePlate + '\'' +
                ", size=" + size +
                '}';
    }

    /**
     * the equals method
     * @param o the object to check if its equal with
     * @return if the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return licencePlate.equals(vehicle.licencePlate) &&
                size == vehicle.size;
    }
}
