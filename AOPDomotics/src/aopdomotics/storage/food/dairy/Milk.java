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
public class Milk extends Dairy {

    public Milk(int quantity, int storageLimit, int lowerLimit) {
        super(quantity, storageLimit, lowerLimit);
    }

    public Milk(float price){
        super(price);
    }
}
