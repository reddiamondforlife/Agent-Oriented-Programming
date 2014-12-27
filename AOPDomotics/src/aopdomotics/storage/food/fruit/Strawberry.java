/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage.food.fruit;

/**
 *
 * @author Daan
 */
public class Strawberry extends Fruit{

    public Strawberry(int quantity, int storageLimit, int lowerLimit) {
        super(quantity, storageLimit, lowerLimit);
    }
    
    public Strawberry(float price){
        super(price);
    }
    
     @Override
    public String toString() {
        return "Stawberry: " + super.getQuantity() + " left"; //To change body of generated methods, choose Tools | Templates.
    }
}
