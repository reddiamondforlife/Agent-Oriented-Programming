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
public class Beef extends Meat{

    public Beef(int quantity, int storageLimit, int lowerLimit) {
        super(quantity, storageLimit, lowerLimit);
    }
    
    public Beef(float price){
        super(price);
    }
    
}
