/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage.food.dairy;

/**
 *
 * @author Daan
 */
public class Cheese  extends Dairy {

    public Cheese(int quantity, int storageLimit, int lowerLimit) {
        super(quantity, storageLimit, lowerLimit);
    }
    
    public Cheese(float price){
        super(price);
    }
    
     @Override
    public String toString() {
        return "Cheese: " + super.getQuantity() + " left"; //To change body of generated methods, choose Tools | Templates.
    }
}
