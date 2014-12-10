/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage;

import aopdomotics.storage.food.Food;

/**
 *
 * @author Daan
 */
public class Recipe {
    Food component1;
    Food component2;
    Food component3;

    public Recipe(Food component1, Food component2, Food component3) {
        this.component1 = component1;
        this.component2 = component2;
        this.component3 = component3;
    }

    public Food getComponent1() {
        return component1;
    }

    public Food getComponent2() {
        return component2;
    }

    public Food getComponent3() {
        return component3;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
