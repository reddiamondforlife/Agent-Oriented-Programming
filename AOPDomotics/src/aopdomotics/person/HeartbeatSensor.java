/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.person;

/**
 *
 * @author Daan
 */
public class HeartbeatSensor {
    
    int stress;

    public HeartbeatSensor() {
    }
    
    public int getStressLevel(){
        generateStressLevel();
        return stress;
    }
    
    public void generateStressLevel() {
        stress = (int) Math.pow(Math.random() * 10.0,2.0);//getBloodPressure(); in physical
        //System.out.println("New Stress Level " + stress);
    }
}
