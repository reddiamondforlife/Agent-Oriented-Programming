/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage.food.meat;

/**
 *
 * @author Daan
 */
public class Bacon extends Meat{

    public Bacon(int quantity, int storageLimit, int lowerLimit) {
        super(quantity, storageLimit, lowerLimit);
    }
    
    public Bacon(float price){
        super(price);
    }
    
     @Override
    public String toString() {
        return "Bacon: " + super.getQuantity() + " left"; //To change body of generated methods, choose Tools | Templates.
    }
}
