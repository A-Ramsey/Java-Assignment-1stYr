/**
 * @author Aaron Ramsey (aar17)
 * @version 1.0
 */

import java.util.Objects;

/**
 * a class to create an attendant
 */
public class Attendant {
    private String name;
    private boolean free;

    /**
     * the empty constructor
     */
    public Attendant(){
        this.name = null;
        this.free = true;
    }

    /**
     * the constructor that takes the name
     * @param name name of the attendant
     */
    public Attendant(String name){
        this.name = name;
        this.free = true;
    }

    /**
     * the constructor that sets all the attributes
     * @param name name of the attendant
     * @param free if they are free or not
     */
    public Attendant(String name, boolean free){
        this.name = name;
        this.free = free;
    }

    /**
     * gets the name of the attendant
     * @return name of the attendant
     */
    public String getName(){
        return this.name;
    }

    /**
     * sets the name of the attendant
     * @param name name of the attendant
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * checks if the attendant is free
     * @return if the attendant is free or not
     */
    public boolean isFree(){
        return this.free;
    }

    /**
     * uses the attendant
     */
    public void useAttendant(){
        this.free = false;
    }

    /**
     * makes the attendant free
     */
    public void makeAttendantFree(){
        this.free = true;
    }

    /**
     * checks if the name of an attendant equals one entered in a string
     * @param that a name to check
     * @return if the name equals the attendants or not
     */
    public boolean nameEquals(String that){
        if (this.name.equals(that)){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * the to string method
     * @return the attendant in a string
     */
    @Override
    public String toString() {
        return "Attendant{" +
                "name='" + name + '\'' +
                ", free=" + free +
                '}';
    }

    /**
     * the equals method
     * @param o the object to check with
     * @return if the 2 ojects are equal
     */
    @Override
    public boolean equals(Object o) {
        Attendant attendant = (Attendant) o;
        return Objects.equals(name, attendant.name);
    }
}