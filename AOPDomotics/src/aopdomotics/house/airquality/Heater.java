package aopdomotics.house.airquality;

/**
 * @author Nick Leijenhorst
 *
 * This class represents a simple heater. The heater can be switched on or off,
 * and you can retrieve the heater status.
 */
public class Heater {

    private boolean heaterOn = false;

    public Heater() {
        System.out.println("Heater object initialized.");
    }

    public void turnOn() {
        heaterOn = true;
        System.out.println("Heater has been turned on");
    }

    public void turnOff() {
        heaterOn = false;
        System.out.println("Heater has been turned off");
    }

    public boolean isOn() {
        return heaterOn;
    }

}
