/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage.food.grains;

/**
 *
 * @author Daan
 */
public class Bread extends Grains{

    public Bread(int quantity, int storageLimit, int lowerLimit) {
        super(quantity, storageLimit, lowerLimit);
    }
    
    public Bread(float price){
        super(price);
    }
    
     @Override
    public String toString() {
        return "Bread: " + super.getQuantity() + " left"; //To change body of generated methods, choose Tools | Templates.
    }
}

