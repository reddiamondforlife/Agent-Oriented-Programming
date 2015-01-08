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
public class Corn extends Grains{

    public Corn(int quantity, int storageLimit, int lowerLimit) {
        super(quantity, storageLimit, lowerLimit);
    }
    
    public Corn(float price){
        super(price);
    }
    
     @Override
    public String toString() {
        return "Corn: " + super.getQuantity() + " left"; //To change body of generated methods, choose Tools | Templates.
    }
}
