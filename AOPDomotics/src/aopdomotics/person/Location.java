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
public class Location {
    float latitude;
    float longitude;
    
    public float getLatitude(){
        generateLatitude();
        return latitude;
    }
    public float getLongitude(){
        generateLongitude();
        return longitude;
    }
    public void generateLatitude(){
      Random r = new Random();
    float Low = 0f;
    float High = 50f;
    latitude = r.nextFloat()*(High-Low) + Low;    
    }
    public void generateLongitude(){
         Random r = new Random();
    float Low = 0f;
    float High = 50f;
    longitude = r.nextFloat()*(High-Low) + Low;     
    }
}
