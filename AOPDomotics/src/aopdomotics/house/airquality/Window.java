package aopdomotics.house.airquality;

import aopdomotics.Helper;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * @author Nick Leijenhorst
 * 
 * This class represents a window object. You can open and close the window, and retrieve the current status, either open or closed, as a boolean.
 */

public class Window {
    
    private boolean windowOpen = false;


    public Window()
    {
        System.out.println("Window object initialized.");
    }
    
    public void openWindow()
    {
        windowOpen = true;
        System.out.println("Window has been opened");
    }
    
    public void closeWindow()
    {
        windowOpen = false;
        System.out.println("Window has been closed");
    }
    
    public boolean isWindowOpen()
    {
        return windowOpen;
    }
  
}
