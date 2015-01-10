package aopdomotics.house.airquality;

import aopdomotics.Helper;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
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
public class AirQualityAgent extends Agent
{
    private float temperature = 20; //Actual temperature in degrees celcius
    private float comfortableTemperature = 20; //This is the temperature setpoint, confortable for humans
    private float temperatureMargin = 1; //This is the margin around the confortable temperature in degrees.
    
    private Window window; //This class only holds one Window object to control, you can purchase the Window DLC for additional windows.
    private Heater heater; //This class only holds one Heater object to control, you can purchase the Heater DLC for additional heaters.
    
    private Random r;
    
    
    
    protected void setup()
    {
        // Printout a welcome message
        System.out.println("Hello! AirQuality-agent " + getAID().getName() + " is ready.");
        Helper.registerAgent(this, getAID(), "airquality-agent", "JADE-AirQuality-Agent");
        
        r = new Random();
        
        try
        {
            Thread.sleep(1000);
        } 
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }

        window = new Window();
        window.openWindow();
        heater = new Heater();
        heater.turnOn();

        addBehaviour(new ComfortHandler());
        
        addBehaviour(new SimulateAirFlow(this, 1000));
    }
    
    public void printAirTemperature()
    {    
        System.out.print("Current temperature: ");
        System.out.printf("%.1f", temperature);
        System.out.println(" C");
    }
   
    
    /**
     * This method is periodically called by the AirSensor object. This will change the temperature a little bit using random values.
     */
    public void temperatureFluctuation()
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
    public void checkAirTemperature()
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
    
    /**
     * Tick every time and fuctuate temperature and check the air temperature
     */
    public class SimulateAirFlow extends TickerBehaviour{

        public SimulateAirFlow(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            temperatureFluctuation();
            checkAirTemperature();
            //printAirTemperature();
        }
        
        
    }
    
    /**
     * Wait for new comfort level from any agent
     */
    public class ComfortHandler extends CyclicBehaviour{

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchConversationId("comfort-update");
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it 
                String content = msg.getContent();
                //System.out.println("Comfort handler");
                float comfort = Float.parseFloat(content);
                //System.out.println("Comfort level : " + comfort);
                comfortableTemperature = comfort;
            } else {
                block();
            }
        }
        
    }
}
