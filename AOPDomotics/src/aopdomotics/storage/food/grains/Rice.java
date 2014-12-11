/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage.food.grains;

/**
 *
 * @author Daan
 */
public class Rice extends Grains{

    public Rice(int quantity, int storageLimit, int lowerLimit) {
        super(quantity, storageLimit, lowerLimit);
    }
 
    public Rice(float price){
        super(price);
    }
    
}
