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
public class Lemon extends Fruit {

    public Lemon(int quantity, int storageLimit, int lowerLimit) {
        super(quantity, storageLimit, lowerLimit);
    }
    
    public Lemon(float price){
        super(price);
    }
}
