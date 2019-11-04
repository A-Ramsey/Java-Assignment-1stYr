/**
 * @author Aaron Ramsey (aar17)
 * @version 1.0
 */

/**
 * an enumerated type to create the sizes of vehicles
 */
public enum Size {
    STANDARD,
    LONG,
    HIGH,
    COACH,
    MOTORBIKE,;

    /**
     * a method to check if the enum type equals a string passed to it
     * @param that the string to check
     * @return whether they are equal or not
     */
    public boolean stringEquals(String that){
        if(this.toString().toLowerCase().equals(that.toLowerCase())){
            return true;
        }
        else {
            return false;
        }
    }
}
