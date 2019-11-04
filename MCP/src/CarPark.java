/**
 * @author Aaron Ramsey (aar17)
 * @version 1.0
 */

import java.io.*;
import java.util.ArrayList;

/**
 * a class to create the the car park with zones and attendants
 */
public class CarPark {
    private ArrayList<Zone> zones;
    private ArrayList<Attendant> attendants;
    private ArrayList<Driver> drivers;
    private ArrayList<ExitToken> exitTokens;
    private float disabledAdjust;
    private float disabledSunday;

    /**
     * the empty constructor method
     */
    public CarPark(){
        zones = new ArrayList<>();
        attendants = new ArrayList<>();
        drivers = new ArrayList<>();
        exitTokens = new ArrayList<>();
    }

    /**
     * a method used to create a new vehicle
     * @param licence the licence plate of the car
     * @param vehicleSizeTemp the size of the vehicle
     * @return the new vehicle created
     */
    public Vehicle createVehicle(String licence, String vehicleSizeTemp){
        Size vehicleSize = null;
        while (vehicleSize == null) {
            switch (vehicleSizeTemp) {
                case "1":
                    vehicleSize = Size.STANDARD;
                    break;
                case "2":
                    vehicleSize = Size.LONG;
                    break;
                case "3":
                    vehicleSize = Size.HIGH;
                    break;
                case "4":
                    vehicleSize = Size.COACH;
                    break;
                case "5":
                    vehicleSize = Size.MOTORBIKE;
                    break;
                case "e":
                    break;
                default:
                    System.out.println("Invalid Size");
                    break;
            }
        }
        Vehicle newVehicle = new Vehicle(licence, vehicleSize);
        return newVehicle;
    }

    /**
     * gets the disabled adjustment cost
     * @return the amount to adjust by for disabled users
     */
    public float getDisabledAdjust() {
        return disabledAdjust;
    }

    /**
     * the method to set the amount to adjust the disabled cost by
     * @param disabledAdjust the disabled adjustment to set
     */
    public void setDisabledAdjust(float disabledAdjust) {
        this.disabledAdjust = disabledAdjust;
    }

    /**
     * gets the disabled cost ona sunday
     * @return the disabled cost on the Sunday
     */
    public float getDisabledSunday() {
        return disabledSunday;
    }

    /**
     * sets the disabled cost on a suday
     * @param disabledSunday cost to park on a Sunday
     */
    public void setDisabledSunday(float disabledSunday) {
        this.disabledSunday = disabledSunday;
    }

    /**
     * saves the current status of the car park to a text file
     * @throws IOException
     */
    public void saver() throws IOException {
        String stateFile = "state.txt";
        try (FileWriter fileWrite = new FileWriter(stateFile);
             BufferedWriter bufferWrite = new BufferedWriter(fileWrite);
             PrintWriter outfile = new PrintWriter(bufferWrite);) {

            for (Attendant a : attendants) {
                outfile.print(a.getName());
                outfile.print(":");
                outfile.println(a.isFree());
            }

            outfile.println("-");

            for (Driver d : drivers) {
                outfile.print(d.getVehicle().toSave());
                outfile.print(":");
                outfile.println(d.getReceipt().toSave());
            }

            outfile.println("-");

            for (ExitToken e : exitTokens) {
                outfile.println(e.toSave());
            }
        }

        String zoneFile = "costs.txt";
        try (FileWriter fileWrite = new FileWriter(zoneFile);
             BufferedWriter bufferWrite = new BufferedWriter(fileWrite);
             PrintWriter outfile = new PrintWriter(bufferWrite);) {

            outfile.print("disabled:");
            outfile.print(disabledAdjust);
            outfile.print(":");
            outfile.println(disabledSunday);

            for (Zone z : zones) {
                outfile.print(z.getId());
                outfile.print(":");
                outfile.println(z.getCost());
            }

        }
    }

    /**
     * gets the ArrayList of exit tokens
     * @return the ArrayList of exit tokens
     */
    public ArrayList<ExitToken> getExitTokens() {
        return exitTokens;
    }

    /**
     * adds an exit token to the ArrayList
     * @param exitToken the exit token to add
     */
    public void addExitToken(ExitToken exitToken) {
        this.exitTokens.add(exitToken);
    }

    /**
     * removes an exit token from the ArrayList
     * @param exitToken exit token to remove
     */
    public void removeExitToken(ExitToken exitToken) {
        this.exitTokens.remove(exitToken);
    }

    /**
     * sets the drivers
     * @param drivers arrayList of the drivers to set the ArrayList to
     */
    public void setDrivers(ArrayList<Driver> drivers) {
        this.drivers = drivers;
    }

    /**
     * sets the exit tokens ArrayList
     * @param exitTokens ArrayList of exit tokens
     */
    public void setExitTokens(ArrayList<ExitToken> exitTokens) {
        this.exitTokens = exitTokens;
    }

    /**
     * gets the ArrayList of the drivers
     * @return the ArrayList of the drivers
     */
    public ArrayList<Driver> getDrivers() {
        return drivers;
    }

    /**
     * adds the driver to the ArrayList
     * @param driver driver to add
     */
    public void addDriver(Driver driver) {
        this.drivers.add(driver);
    }

    /**
     * removes driver from the ArrayList of drivers
     * @param driver the driver to remove
     */
    public void removeDriver(Driver driver) {
        this.drivers.remove(driver);
    }

    /**
     * gets the zones
     * @return the zones
     */
    public ArrayList<Zone> getZones() {
        return zones;
    }

    /**
     * adds a zone to the system
     * @param zone zone to add
     */
    public void addZone(Zone zone) {
        this.zones.add(zone);
    }

    /**
     * sets the zones
     * @param zones ArrayList of Zones to set the ArrayList to
     */
    public void setZones(ArrayList<Zone> zones) {
        this.zones = zones;
    }

    /**
     * sets the attendants
     * @param attendants ArrayList of attendants to set it to
     */
    public void setAttendants(ArrayList<Attendant> attendants) {
        this.attendants = attendants;
    }

    /**
     * gets the ArrayList of attendants
     * @return ArrayList of Attendants
     */
    public ArrayList<Attendant> getAttendants() {
        return attendants;
    }

    /**
     * add an attendant
     * @param attendant attendant to add
     */
    public void addAttendant(Attendant attendant) {
        attendants.add(attendant);
    }

    /**
     * removes an attendant
     * @param attendant the attendant to remove
     */
    public void removeAttendant(Attendant attendant) {
        attendants.remove(attendant);
    }

    /**
     * finds the zones which match size with the vehicle
     * @param size size of the vehicle
     * @return ArrayList of zones that match
     */
    public ArrayList<Zone> findSizeMatches(Size size) {
        ArrayList<Zone> zoneMatches = new ArrayList<Zone>();
        for (Zone z: zones) {
            for (Size si : z.getSize()) {
                if (si.equals(size)) {
                    zoneMatches.add(z);
                    break;
                }
            }
        }
        return zoneMatches;
    }

    /**
     * finds the free spaces in a zone
     * @param z zone to check for free spaces
     * @return the free spaces
     */
    public ArrayList<Space> findFreeSpaces(Zone z) {
        ArrayList<Space> freeSpaces = new ArrayList<Space>();
        //loops through the spaces in the zones to find the free spaces
        for (Space sp : z.getSpaces()) {
            if (sp.getOccupiedBy() == null) {
                freeSpaces.add(sp);
            }
        }
        return freeSpaces;
    }

    /**
     * to string method
     * @return the car park turned into a string
     */
    @Override
    public String toString() {
        return "CarPark{" +
                "zones=" + zones +
                ", attendants=" + attendants +
                ", drivers=" + drivers +
                '}';
    }
}
