package aopdomotics.house.airquality;

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
