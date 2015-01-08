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
public class Tomato extends Vegetable{

    public Tomato(int quantity, int storageLimit, int lowerLimit) {
        super(quantity, storageLimit, lowerLimit);
    }
    
    public Tomato(float price){
        super(price);
    }
    
     @Override
    public String toString() {
        return "Tomato: " + super.getQuantity() + " left"; //To change body of generated methods, choose Tools | Templates.
    }
}
