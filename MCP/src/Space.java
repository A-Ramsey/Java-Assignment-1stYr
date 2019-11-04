/**
 * @author Aaron Ramsey (aar17)
 * @version 1.0
 */

/**
 * a class to create the spaces
 */
public class Space {
    private int spaceId;
    private Vehicle occupiedBy;
    private float cost;

    /**
     * empty constructor
     */
    public Space(){}

    /**
     * basic constructor which sets the space id
     * @param id the id of the space
     */
    public Space(int id, float cost){
        this.spaceId = id;
        this.cost = cost;
    }

    /**
     * constructor that sets the id and the occupier of the space
     * @param id the id of the space
     * @param cost the cost per hour of the space
     * @param occupier the vehicle occupying the space
     */
    public Space(int id, float cost, Vehicle occupier){
        this.spaceId = id;
        this.occupiedBy = occupier;
        this.cost = cost;
    }

    /**
     * gets the space id
     * @return the space id
     */
    public int getSpaceId() {
        return spaceId;
    }

    /**
     * sets the space id
     * @param spaceId the space id
     */
    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    /**
     * gets who the space is occupied by
     * @return who the space is occupied by
     */
    public Vehicle getOccupiedBy() {
        return occupiedBy;
    }

    /**
     * sets who the space is occupied by
     * @param occupiedBy who the space is occupied by
     */
    public void setOccupiedBy(Vehicle occupiedBy) {
        this.occupiedBy = occupiedBy;
    }

    /**
     * gets the cost of the space per hour
     * @return cost of the space per hour
     */
    public float getCost() {return this.cost;}

    /**
     * matches the id of a space
     * @param id id to check
     * @return if they match or not
     */
    public boolean idMatch(int id) {
        return this.spaceId == id;
    }

    /**
     * the to string method
     * @return the data in a string
     */
    @Override
    public String toString() {
        return "Space{" +
                "spaceId=" + spaceId +
                ", occupiedBy=" + occupiedBy +
                ", cost=" + cost +
                '}';
    }

    /**
     * the equals method
     * @param o the object to check if they match
     * @return if the objects match
     */
    @Override
    public boolean equals(Object o) {
        Space space = (Space) o;
        return spaceId == space.spaceId;
    }
}
