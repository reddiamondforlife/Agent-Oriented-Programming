/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage.food.dairy;

import aopdomotics.storage.food.Food;

/**
 *
 * @author Daan
 */
public class Dairy extends Food {

    public Dairy(int quantity, int storageLimit, int lowerLimit) {
        super(quantity, storageLimit, lowerLimit);
    }
    
    public Dairy(float price){
        super(price);
    }
    
     @Override
    public String toString() {
        return "Dairy: " + super.getQuantity() + " left"; //To change body of generated methods, choose Tools | Templates.
    }
}
