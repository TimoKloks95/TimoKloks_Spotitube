package nl.han.oose.timokloks.util;

public class AvailabilityConverter {

    public AvailabilityConverter() {

    }

    public boolean convertToClient(int availability) {
        if (availability == 1) {
            return true;
        } else if (availability == 0) {
            return false;
        } else {
            throw new RuntimeException("Invalid input");
        }
    }

    public int convertToDatabase(boolean availibility) {
        if (availibility) {
            return 1;
        } else {
            return 0;
        }
    }
}
