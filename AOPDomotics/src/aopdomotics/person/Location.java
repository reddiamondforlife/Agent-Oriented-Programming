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
  String[] locations = {"Home","Work","Metro","Bike","Car","Somwhere"};
    
    public String getLocation(){
        int idx = new Random().nextInt(locations.length);
        String random = (locations[idx]);
        return random;
    }
   
  
}
