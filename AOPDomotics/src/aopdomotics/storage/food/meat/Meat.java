/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage.food.meat;

import aopdomotics.storage.food.Food;

/**
 *
 * @author Daan
 */
public class Meat extends Food{

    public Meat(int quantity, int storageLimit, int lowerLimit) {
        super(quantity, storageLimit, lowerLimit);
    }
    
    public Meat(float price){
        super(price);
    }
    
}
