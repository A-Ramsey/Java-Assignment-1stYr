/**
 * @author Aaron Ramsey (aar17)
 * @version 1.0
 */

import static java.lang.System.currentTimeMillis;

/**
 * a class to create a basic outline of a token with a code and a time
 */
public abstract class CodeTime {
    int code;
    long startTime;

    /**
     * empty constructor
     */
    public CodeTime(){}

    /**
     * constructor which takes a code
     * @param code code to save with
     */
    public CodeTime(int code) {
        this.code = code;
        this.startTime = currentTimeMillis();
    }

    /**
     * constructor to use when reading in from a file as it takes the start time as a parameter
     * @param code the code to save it with
     * @param time the time the receipt was generated
     */
    public CodeTime(int code, long time) {
        this.code = code;
        this.startTime = time;
    }

    /**
     * returns the code
     * @return the code
     */
    public int getCode(){
        return code;
    }

    /**
     * gets the start time that the receipt was generated with
     * @return the start time
     */
    public long getStartTime(){
        return startTime;
    }

    /**
     * returns the data in the format for storing in a file
     * @return data to store in a file
     */
    public String toSave() {
        return code + ":" + startTime;
    }

    /**
     * the equals method
     * @param o object to compare with
     * @return whether they match or not
     */
    @Override
    public boolean equals(Object o) {
        CodeTime codeTime = (CodeTime) o;
        return codeTime.code == code;
    }

    /**
     * the to sting method
     * @return the data in a string
     */
    @Override
    public String toString() {
        return "CodeTime{" +
                "code=" + code +
                ", startTime=" + startTime +
                '}';
    }
}
