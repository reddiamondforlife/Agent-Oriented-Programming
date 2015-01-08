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
public class Apple extends Fruit{

    public Apple(int quantity, int storageLimit, int lowerLimit) {
        super(quantity, storageLimit, lowerLimit);
    }
    
    public Apple(float price){
        super(price);
    }
    
     @Override
    public String toString() {
        return "Apple: " + super.getQuantity() + " left"; //To change body of generated methods, choose Tools | Templates.
    }
}
