/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.person;

import java.util.Random;

/**
 *
 * @author Daniel
 */
public class BloodPressure {
    int bloodpressure;
    public int getBloodpressure(){
        generatePressure();
        return bloodpressure;
    }
    public void generatePressure(){
    Random r = new Random();
    int Low = 120;
    int High = 180;
    bloodpressure = r.nextInt(High-Low) + Low;
    }
    
}
