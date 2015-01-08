/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage.food.vegetable;

/**
 *
 * @author Daan
 */
public class Onion extends Vegetable{

    public Onion(int quantity, int storageLimit, int lowerLimit) {
        super(quantity, storageLimit, lowerLimit);
    }
    
    public Onion(float price){
        super(price);
    }
    
     @Override
    public String toString() {
        return "Onions: " + super.getQuantity() + " left"; //To change body of generated methods, choose Tools | Templates.
    }
}
