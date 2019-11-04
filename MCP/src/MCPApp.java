import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Aaron Ramsey (aar17)
 * @version 1.0
 */
public class MCPApp {
    private Scanner scan;
    private int nextCode;
    private int tokenNum;
    private CarPark mcp;

    /**
     * constructor for class
     */
    private MCPApp() {
        Scanner scan = new Scanner(System.in);
        nextCode = 1111;
        tokenNum = 1111;
    }

    /**
     * used to keep it consistent in the amount of '-' i use to break up sections
     */
    private static void lineBreak() {
        System.out.println("--------------------");
    }

    /**
     * outputs the different sizes and an explanations of them
     */
    private static void sizeExplanation(){
        lineBreak();
        System.out.println("Standard: height - upto 2m; length - less than 5m");
        System.out.println("High: height - over 2m, below 3m; length - less than 5m");
        System.out.println("Long: height - less than 3m; length - between 5.1m and 6m");
        System.out.println("Coach: height - upto 3m; length - any");
        System.out.println("Motorbike: any size of motorbike");
        lineBreak();
    }

    /**
     * this is used to get all the inputs of the user to create a new vehicle
     * @param scan the scanner
     * @param mcp the car park
     * @return the vehicle that ahs just been created
     * @throws IOException
     */
    private Vehicle newVehicle(Scanner scan, CarPark mcp) throws IOException {
        //get vehicle registration plate
        System.out.print("Enter vehicle registration plate: ");
        String regPlate = scan.nextLine();

        String temp = "e";
        while (temp.toLowerCase().equals("e")) {
            //get vehicle size
            System.out.print("What size is the vehicle: 1 - Standard, 2 - Long, 3 - High, 4 - Coach, 5 - Motorbike, E - Size explanations?: ");
            temp = scan.nextLine().toLowerCase();
            if (temp.toLowerCase().equals("e")){
                sizeExplanation();
            }
        }
        String vehicleSizeString = temp;

        Vehicle newVehicle = mcp.createVehicle(regPlate, vehicleSizeString);

        return newVehicle;
    }

    /**
     * this method lets the attendant pick the space to park the vehicle in
     * @param freeSpaces ArrayList of all the free spaces in the zones that match sizes
     * @return the space selected by the attendant
     */
    private Space attendantPickSpace(ArrayList<Space> freeSpaces) {
        do {
            System.out.print(freeSpaces);
            int idPicked = scan.nextInt();
            scan.nextLine();
            for (Space s : freeSpaces) {
                if (s.idMatch(idPicked)) {
                    return s;
                }
            }
            System.out.println("ID not recognised");
        } while (true);
    }

    /**
     * checks the codes when it increments them that they aren't currently in use
     * @param mcp the car park
     * @param code the code to check
     * @param isExitToken if it is an exit token or a receipt code
     * @return true is it cant be used or false if it can
     */
    private boolean equalCodes(CarPark mcp, int code, boolean isExitToken) {
        if (isExitToken) {
            for (ExitToken c : mcp.getExitTokens()) {
                if (c.getCode() == code) {
                    return true; //returns true to tell the code has a match in the existing codes
                }
            }
        } else {
            for (Driver d : mcp.getDrivers()) {
                Receipt r = d.getReceipt();
                if (r.getCode() == code) {
                    return true; //returns true to tell the code has a match in the existing codes
                }
            }
        }
        return false;
    }

    /**
     * increments the code by one and calls the equals code method to check if the code ahs been used
     * @param mcp the car park
     * @param code the code to check
     * @param isExitToken if it is an exit token or a receipt code
     * @return
     */
    private int codeChecker(CarPark mcp, int code, boolean isExitToken) {
        if (code == 9999) {
            code = 1111;
        }
        do {
            code++;
        } while (equalCodes(mcp, code, isExitToken));
        return code;
    }

    /**
     * directs the customer to their space
     * @param freeSpaces array of all matching spaces in the zones that match
     * @param zoneToUse the zone that is of size to use
     * @return the space the customer has to park in
     */
    private Space customerPark(ArrayList<Space> freeSpaces, Zone zoneToUse) {
        Space spaceToPark = freeSpaces.get(0);
        System.out.print("Park your car in zone ");
        System.out.print(zoneToUse.getId());
        System.out.print(" in space ");
        System.out.println(spaceToPark.getSpaceId());
        return spaceToPark;
    }

    /**
     * the method used to park the car
     * @param scan the scanner to read in data
     * @param mcp the car park
     * @param isCustomer used to tell if it is a customer or attendant running the method
     * @throws IOException
     */
    private void park(Scanner scan, CarPark mcp, boolean isCustomer) throws IOException {
        lineBreak();
        Vehicle vehicleToPark = newVehicle(scan, mcp);
        Size vehicleSize = vehicleToPark.getSize();

        ArrayList<Zone> zoneMatches = new ArrayList<>();
        zoneMatches = mcp.findSizeMatches(vehicleSize);
        Zone zoneToUse = zoneMatches.get(0);

        ArrayList<Space> freeSpaces = new ArrayList<>();
        freeSpaces = mcp.findFreeSpaces(zoneToUse);

        lineBreak();

        Space spaceToPark;
        if (isCustomer) {
            spaceToPark = customerPark(freeSpaces, zoneToUse);
        } else {
            if (!vehicleToPark.getSize().stringEquals("motorbike") || !vehicleToPark.getSize().stringEquals("coach")) {
                spaceToPark = attendantPickSpace(freeSpaces);
            } else {
                System.out.println("Attendant cannot park a coach or a motorbike");
                spaceToPark = customerPark(freeSpaces, zoneToUse);
            }
        }

        spaceToPark.setOccupiedBy(vehicleToPark);

        Receipt newReceipt = new Receipt(nextCode, spaceToPark.getCost(), zoneToUse, spaceToPark); //keeps the code as an int so it can easily increase it but uses a string to keep it as 4 digits
        nextCode = codeChecker(mcp, nextCode, false);

        Driver newDriver = new Driver(newReceipt, vehicleToPark);
        mcp.addDriver(newDriver);

        System.out.print("Your receipt code is: ");
        System.out.println(newReceipt.getCode());
    }

    /**
     * used to pay and retrieve your car from the car park
     * @param scan the scanner to read data from the user
     * @param mcp the car park
     * @param isCustomer used to tell if it is a customer or an attendant running the method
     * @throws IOException
     */
    private void payAndRetrieve(Scanner scan, CarPark mcp, boolean isCustomer) throws IOException {
        lineBreak();
        System.out.print("Enter receipt code: ");
        int codeEntered = scan.nextInt();
        scan.nextLine();

        for (Driver d : mcp.getDrivers()) {
            Receipt tempReceipt = d.getReceipt();
            if (tempReceipt.codeEquals(codeEntered)) {
                tempReceipt.pay(scan, mcp);

                lineBreak();

                if (isCustomer) {
                    System.out.print("Reminder: You are parked in zone ");
                    System.out.print(tempReceipt.getZoneName());
                    System.out.print(" in space ");
                    System.out.println(tempReceipt.getSpaceName());
                    System.out.print("Your token for the barrier is: ");
                } else {
                    System.out.print("Give this token to your attendant: ");
                }
                System.out.println(tokenNum);
                ExitToken newToken = new ExitToken(tokenNum, 15); //change to set time to leave to be read in from a file
                mcp.addExitToken(newToken);
                tokenNum = codeChecker(mcp, tokenNum, true);

                mcp.removeDriver(d);
                return; //leave the method if it finds a matching code after doing all the required processing
            }
        }
        System.err.println("Receipt code entered did not match any stored"); //will only reach this if the code entered does not have a match
    }

    /**
     * used to leave the car park at the barrier
     * @param scan the scanner object
     * @param mcp the car park
     * @param isCustomer used to tell if it is a customer or attendant running a method
     */
    private void leaveCarPark(Scanner scan, CarPark mcp, boolean isCustomer) {
        lineBreak();
        System.out.print("Input exit token: ");
        int token = scan.nextInt();
        scan.nextLine();
        for (ExitToken e : mcp.getExitTokens()) {
            if (e.getCode() == token) {
                e.leave();
                mcp.removeExitToken(e); //can do this as if seek assistance is used it will be sorted so they dont need to remain in the array
                if (isCustomer) {
                    System.out.println("Leaving the car park!");
                } else {
                    System.out.println("Returned car to customer");
                }
                return; //used to break out of the method when the matching code is found
            }
        }
        System.out.println("Exit token entered did not match any stored"); //will only reach this if the code entered does not have a match
    }

    /**
     * used to add an attendant
     * @param scan scanner object
     * @param mcp the car park
     * @throws IOException
     */
    private void addAttendant(Scanner scan, CarPark mcp) throws IOException {
        lineBreak();
        System.out.print("What is the Attendants name?: ");
        String name = scan.nextLine();
        Attendant newAttendant = new Attendant(name);
        mcp.addAttendant(newAttendant);
        System.out.println("Attendant Added");
    }

    /**
     * the method used to remove attedants
     * @param scan the scanner object
     * @param mcp the car park
     * @throws IOException
     */
    private void removeAttendant(Scanner scan, CarPark mcp) throws IOException {
        lineBreak();
        System.out.print("Name of Attendant to remove: ");
        String name = scan.nextLine();
        for (Attendant a : mcp.getAttendants()) {
            if (a.nameEquals(name)) {
                mcp.removeAttendant(a);
                return;
            }
        }
        System.out.println("Attendant " + name + " is not known.");
    }

    /**
     * used to return the the car after the user has given the code to the attendant
     * an attendant can only access this so there doesnt need to be validation on this
     * @param scan
     * @param mcp
     */
    private void attendantReturnCar(Scanner scan, CarPark mcp) {
        leaveCarPark(scan, mcp, false);
    }

    /**
     * used to get an attendant to use
     * @param mcp the car park
     * @return the attendant to use
     */
    private Attendant getAttendant(CarPark mcp) {
        ArrayList<Attendant> allAttendants = mcp.getAttendants();
        Attendant attendantToUse = null;
        if (allAttendants.size() > 0) {
            attendantToUse = allAttendants.get(0);
        }
        return attendantToUse;
    }

    /**
     * used to activate inactive attendants
     * @param scan the scanner object
     * @param mcp the car park
     */
    private void activateAttendant(Scanner scan, CarPark mcp) {
        lineBreak();
        System.out.print("Name of Attendant to make active: ");
        String name = scan.nextLine();
        for (Attendant a : mcp.getAttendants()) {
            if (a.nameEquals(name)) {
                a.useAttendant();
                return;
            }
        }
        System.out.println("Attendant " + name + " is not known.");
    }

    /**
     * the method used to deactivate attendants
     * @param scan the scanner object
     * @param mcp the car park
     */
    private void deactivateAttendant(Scanner scan, CarPark mcp) {
        lineBreak();
        System.out.print("Name of Attendant to deactivate: ");
        String name = scan.nextLine();
        for (Attendant a : mcp.getAttendants()) {
            if (a.nameEquals(name)) {
                a.makeAttendantFree();
                return;
            }
        }
        System.out.println("Attendant " + name + " is not known.");
    }

    /**
     * prints the menu for the attendants
     */
    private void printAttendantMenu() {
        System.out.println("---Pick an option---");
        System.out.println("1 - Add Attendant");
        System.out.println("2 - Remove Attendant");
        System.out.println("3 - Return Car");
        System.out.println("4 - Activate Attendant");
        System.out.println("5 - Deactivate Attendant");
        System.out.println("b - Back");
    }

    /**
     * runs the menu for the attendants
     * @param scan the scanner object
     * @param mcp the car park
     * @throws IOException
     */
    private void runAttendantMenu(Scanner scan, CarPark mcp) throws IOException {
        String option;
        do {
            lineBreak();
            printAttendantMenu();
            option = scan.nextLine().toLowerCase();
            switch (option) {
                case "1":
                    addAttendant(scan, mcp);
                    break;
                case "2":
                    removeAttendant(scan, mcp);
                    break;
                case "3":
                    attendantReturnCar(scan, mcp);
                    break;
                case "4":
                    activateAttendant(scan, mcp);
                    break;
                case "5":
                    deactivateAttendant(scan, mcp);
                    break;
                case "b":
                    break;
                default:
                    System.err.println("Invalid entry");
                    break;
            }
        } while (!option.equals("b"));
    }

    /**
     * gets the attendant to park the car
     * @param scan the scanner object
     * @param mcp the car park
     * @throws IOException
     */
    private void attendantParkCar(Scanner scan, CarPark mcp) throws IOException {
        lineBreak();
        Attendant attendantToUse = getAttendant(mcp);
        if (attendantToUse != null) {
            if (attendantToUse.isFree()) {
                System.out.print("Your attendant is ");
                System.out.println(attendantToUse.getName());
                attendantToUse.useAttendant();
                park(scan, mcp, false);
                attendantToUse.makeAttendantFree();
            } else {
                System.out.println("There are no attendants available");
            }
        } else {
            System.out.println("There are no attendants available");
        }
    }

    /**
     * gets an attendant to retrieve a vehicle
     * @param scan the scanner object
     * @param mcp teh car park
     * @throws IOException
     */
    private void attendantRetrieveCar(Scanner scan, CarPark mcp) throws IOException {
        lineBreak();
        Attendant attendantToUse = getAttendant(mcp);
        if (attendantToUse != null) {
            if (attendantToUse.isFree()) {
                System.out.print("Your attendant is ");
                System.out.println(attendantToUse.getName());
                attendantToUse.useAttendant();
                payAndRetrieve(scan, mcp, false);
                attendantToUse.makeAttendantFree();
            } else {
                System.out.println("There are no attendants available");
            }
        } else {
            System.out.println("There are no attendants available");
        }
    }

    /**
     * outputs the state of the car park
     * @param mcp the car park
     */
    private void printState(CarPark mcp){
        System.out.println(mcp.getZones());
    }

    /**
     * method to print out options of the system
     */
    private void printMenu() {
        System.out.println("---Pick an option---");
        System.out.println("1 - Park a vehicle");
        System.out.println("2 - Pay parking and retrieve car");
        System.out.println("3 - Leave car park");
        System.out.println("4 - Get attendant to park car");
        System.out.println("5 - Get attendant to retrieve car");
        System.out.println("6 - Attendants area");
        System.out.println("7 - Print state of car park");
        System.out.println("q - Quit");
    }

    /**
     * this runs the menu and selects the options to run
     *
     * @param scan scanner object to get the input
     */
    private void runMenu(Scanner scan) throws IOException {
        String option;
        do {
            lineBreak();
            printMenu();
            option = scan.nextLine().toLowerCase();
            switch (option) {
                case "1":
                    park(scan, mcp, true);
                    break;
                case "2":
                    payAndRetrieve(scan, mcp, true);
                    break;
                case "3":
                    leaveCarPark(scan, mcp, true);
                    break;
                case "4":
                    attendantParkCar(scan, mcp);
                    break;
                case "5":
                    attendantRetrieveCar(scan, mcp);
                    break;
                case "6":
                    runAttendantMenu(scan, mcp);
                    break;
                case "7":
                    printState(mcp);
                    break;
                case "q":
                    break;
                default:
                    System.err.println("Invalid entry");
                    break;
            }
        } while (!option.equals("q"));
    }

    /**
     * this is the method for when the app starts
     */
    private void starter() throws IOException {

        mcp = new CarPark();
        /*make this load the current state*/

        //used to load in the zone costs, sizes and disabled costs into
        ArrayList<ArrayList<Size>> sizes = new ArrayList<>(5);
        ArrayList<Float> zoneCosts = new ArrayList<>();

        //loads the costs etc...
        FileReader fileInput = new FileReader("costs");
        Scanner costFile = new Scanner(fileInput);
        costFile.useDelimiter(":|\r?\n|\r");
        String temp;
        String sizeTemp;
        String sizeTemp2 = "";
        char[] sizeTemp3;

        costFile.next();
        mcp.setDisabledAdjust(Float.parseFloat(costFile.next()));
        mcp.setDisabledSunday(Float.parseFloat(costFile.next()));
        while (costFile.hasNext()) {
            costFile.next();
            zoneCosts.add(Float.valueOf(costFile.next()));

            /*ArrayList<Size> sizeTemp4 = new ArrayList<Size>();
            sizeTemp = costFile.next();
            sizeTemp3 = sizeTemp.toCharArray();
            System.out.println(sizeTemp);
            for (Character c: sizeTemp3){
                if (!c.equals(',')){
                    sizeTemp2 += c;
                }
                else {
                    System.out.print("Size temp: ");
                    System.out.println(sizeTemp2);
                    System.out.print("Size values: ");
                    for (Size s: Size.values()){
                        System.out.println(s);
                        if (s.stringEquals(sizeTemp2)){
                            sizeTemp4.add(s);
                            break;
                        }
                    }
                }
            }
            if (sizeTemp4.size() == 0) {
                //add code to deal with this
                System.err.println("No valid size provided");
            }
            sizes.add(sizeTemp4);
            sizeTemp4.clear();*/

        }

        //change this to read from the costs file//////////////////////////////////////////////
        ArrayList<Size> zone1 = new ArrayList<>();
        zone1.add(Size.STANDARD);
        zone1.add(Size.HIGH);
        sizes.add(zone1);
        ArrayList<Size> zone2 = new ArrayList<>();
        zone2.add(Size.LONG);
        sizes.add(zone2);
        ArrayList<Size> zone3 = new ArrayList<>();
        zone3.add(Size.COACH);
        sizes.add(zone3);
        ArrayList<Size> zone4 = new ArrayList<>();
        zone4.add(Size.STANDARD);
        sizes.add(zone4);
        ArrayList<Size> zone5 = new ArrayList<>();
        zone5.add(Size.MOTORBIKE);
        sizes.add(zone5);

        //creates the zones and adds spaces to the zones
        ArrayList<Zone> zones = new ArrayList<>();
        for (int i = 0; i < (zoneCosts.size()); i++) {
            Zone zoneTemp = new Zone((i + 1), zoneCosts.get(i), sizes.get(i));
            for (int j = 0; j < 20; j++) {
                Space tempSpace = new Space(j, zoneCosts.get(i));
                zoneTemp.addSpace(tempSpace);
            }
            zones.add(zoneTemp);
        }
        mcp.setZones(zones);

        //returning scan to read from the system in
        scan = new Scanner(System.in);
        System.out.println("-----Welcome to the Multistory car park app-----");
    }

    /**
     * this is the method for when the app ends
     */
    private void quitter () throws IOException{
        /*make this save the current state*/
        mcp.saver();
        System.out.println("State saved");
        System.out.println("Thank you for using the car park app");
        lineBreak();
    }

    /**
     * main method that the system runs from
     * @param args list of string of arguements
     */
    public static void main (String[]args) throws IOException {
        MCPApp app = new MCPApp();
        //creates a new car park
        app.starter();
        app.runMenu(app.scan);
        app.quitter();
    }
}