/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage.food.fruit;

import aopdomotics.storage.food.Food;

/**
 *
 * @author Daan
 */
public class Fruit extends Food{

    public Fruit(int quantity, int storageLimit, int lowerLimit) {
        super(quantity, storageLimit, lowerLimit);
    }
    
    public Fruit(float price){
        super(price);
    }
    
}
