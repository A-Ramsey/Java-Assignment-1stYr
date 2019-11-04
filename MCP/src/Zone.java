/**
 * @author Aaron Ramsey (aar17)
 * @version 1.0
 */

import java.util.ArrayList;

/**
 * a class to create the zones
 */
public class Zone {
    private int id;
    private ArrayList<Space> spaces;
    private float cost;
    private ArrayList<Size> size;

    /**
     * the empty constructor
     */
    public Zone(){}

    /**
     * the constructor to fully build a zone
     * @param id the id of the zone
     * @param cost the cost to park in the zone
     * @param sizes the sizes of vehicle allowed in the zone
     */
    public Zone(int id, float cost, ArrayList<Size> sizes){
        this.id = id;
        this.cost = cost;
        this.size = sizes;
        this.spaces = new ArrayList<Space>();
    }

    /**
     * gets the id of the zone
     * @return the id of the zone
     */
    public int getId() {
        return id;
    }

    /**
     * sets the id of the zone
     * @param id takes the id of the zone
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets the spaces in the zone
     * @return all the spaces in the zone
     */
    public ArrayList<Space> getSpaces() {
        return spaces;
    }

    /**
     * add spaces to the zone
     * @param spaces spaces to add
     */
    public void addSpaces(ArrayList<Space> spaces) {
        for (Space s: spaces) {
            this.spaces.add(s);
        }
    }

    /**
     * adds a singular space to a zone
     * @param s space to add
     */
    public void addSpace(Space s){
        this.spaces.add(s);
    }

    /**
     * removes spaces from a zone
     * @param spaces spaces to remove from a zone
     */
    public void removeSpaces(ArrayList<Space> spaces){
        for (Space s: spaces) {
            this.spaces.remove(s);
        }
    }

    /**
     * get the cost of a zone
     * @return the cost of a zone
     */
    public float getCost() {
        return cost;
    }

    /**
     * set the cost of a zone
     * @param cost the cost of a zone
     */
    public void setCost(float cost) {
        this.cost = cost;
    }

    /**
     * the sizes allowed in the zone
     * @return the allowed sizes for that zone
     */
    public ArrayList<Size> getSize() {
        return size;
    }

    /**
     * adds sizes to the zone
     * @param size size to add
     */
    public void addSizes(ArrayList<Size> size) {
        for (Size s: size){
            this.size.add(s);
        }
    }

    /**
     * removes the sizes from the zone
     * @param size size to remove from the zone
     */
    public void removeSizes(ArrayList<Size> size) {
        for (Size s: size){
            this.size.remove(s);
        }
    }

    /**
     * the to sting method
     * @return the data in a string
     */
    @Override
    public String toString() {
        return "Zone{" +
                "id=" + id +
                ", spaces=" + spaces +
                ", cost=" + cost +
                ", size=" + size +
                '}';
    }
}
