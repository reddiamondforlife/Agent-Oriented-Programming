/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage;

import aopdomotics.storage.food.dairy.*;
import aopdomotics.storage.food.fruit.*;
import aopdomotics.storage.food.grains.*;
import aopdomotics.storage.food.meat.*;
import aopdomotics.storage.food.vegetable.*;

/**
 *
 * @author Daan
 */
public class Store {
    //Dairy
    Butter butter;
    Cheese cheese;
    Milk milk;
    
    //Fruit
    Apple apple;
    Lemon lemon;
    Strawberry strawberry;
    
    //Grains
    Bread bread;
    Corn corn;
    Rice rice;
    
    //Meat
    Bacon bacon;
    Beef beef;
    Chicken chicken;
    
    //Vegetable
    Onion onion;
    Potato potato;
    Tomato tomato;

    public Store(Butter butter, Cheese cheese, Milk milk, Apple apple, Lemon lemon, Strawberry strawberry, Bread bread, Corn corn, Rice rice, Bacon bacon, Beef beef, Chicken chicken, Onion onion, Potato potato, Tomato tomato) {
        this.butter = butter;
        this.cheese = cheese;
        this.milk = milk;
        this.apple = apple;
        this.lemon = lemon;
        this.strawberry = strawberry;
        this.bread = bread;
        this.corn = corn;
        this.rice = rice;
        this.bacon = bacon;
        this.beef = beef;
        this.chicken = chicken;
        this.onion = onion;
        this.potato = potato;
        this.tomato = tomato;
    }
    
    
    
}
