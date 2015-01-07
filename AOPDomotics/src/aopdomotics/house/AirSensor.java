package aopdomotics.house;

import java.util.Random;

/**
 * @author Nick Leijenhorst
 * 
 * This class changes and monitors air temperature. It runs on a separate thread and will change the air temperature based on open windows and heater.
 * 
 * Every cycle, the temperature is evaluated. If the temperature is below the setpoint (comfortable temperature - margin) then this class will close the window and start the heater.
 * If the temperature is above the setpoint (comfortable temperature + margin) then the window will be opened and the heater will be disabled.
 * 
 * Obviously this is an inefficient system, but in order to show off the system, large temperature changes will have to occur over a short time scale. It is for demo purposes.
 */
public class AirSensor extends Thread implements Runnable
{
    private float temperature = 20; //Actual temperature in degrees celcius
    private float comfortableTemperature = 20; //This is the temperature setpoint, confortable for humans
    private float temperatureMargin = 1; //This is the margin around the confortable temperature in degrees.
    
    private Window window; //This class only holds one Window object to control, you can purchase the Window DLC for additional windows.
    private Heater heater; //This class only holds one Heater object to control, you can purchase the Heater DLC for additional heaters.
    
    private Random r;
    
    public AirSensor(Window w, Heater h)
    {
        this.window = w;
        this.heater = h;
        
        r = new Random();
        
        System.out.println("Air sensor initialized.");
    }
    
    public void printAirTemperature()
    {    
        System.out.print("Current temperature: ");
        System.out.printf("%.1f", temperature);
        System.out.println(" C");
    }
    
    public void run()
    {
        while(true)
        {
            try
            {
                temperatureFluctuation();
                checkAirTemperature();
                printAirTemperature();
                        
                Thread.sleep(1000);
            } 
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * This method is periodically called by the AirSensor object. This will change the temperature a little bit using random values.
     */
    private void temperatureFluctuation()
    {
        //This causes a random fluctuation every cycle
        float fluctuation = r.nextFloat() - 0.5f;
        temperature += (fluctuation/5);
        
        //If window is open, the room cools down
        if(window.isWindowOpen())
        {
            temperature -= 0.1;
        }
        
        //If heater is on, the room warms up
        if(heater.isOn())
        {
            temperature += 0.1;
        }
    }
    
    /**
     * This method is periodically called by the AirSensor object to check if windows have to open, or heaters have to turn on.
     */
    private void checkAirTemperature()
    {
        //If it is too cold... Close the window and turn on heater...
        if(temperature < (comfortableTemperature - temperatureMargin))
        {
            window.closeWindow();
            heater.turnOn();
        }
        
        //If it is too warm... Open the window and turn off heater...
        if(temperature > (comfortableTemperature + temperatureMargin))
        {
            window.openWindow();
            heater.turnOff();
        }
    }
    
    public void setConfortableTemperature(float temperature)
    {
        this.comfortableTemperature = temperature;
    }
    
}
