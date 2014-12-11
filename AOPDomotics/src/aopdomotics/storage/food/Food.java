/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage.food;

/**
 *
 * @author Daan
 */
public class Food {
    int quantity;
    int storageLimit;
    int lowerLimit;
    float price = 1000000f;

    public Food(int quantity, int storageLimit, int lowerLimit) {
        this.quantity = quantity;
        this.storageLimit = storageLimit;
        this.lowerLimit = lowerLimit;
    }
    
    public Food(float price){
        this.price = price;
    }

    public float getPrice() {
        return price;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if(quantity < 0){
            this.quantity = 0;
        } else if(quantity > storageLimit){
            this.quantity = storageLimit;
        } else {
            this.quantity = quantity;
        }
    }

    public int getLowerLimit() {
        return lowerLimit;
    }
    
    
    
    public int buyQuantity(){
        if(quantity < lowerLimit){
            return storageLimit - quantity;
        }
        return 0;
    }
}
