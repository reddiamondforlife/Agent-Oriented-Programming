/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage.recipe;

import aopdomotics.storage.food.Food;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 *
 * @author Daan
 */
public class Recipe {
    String name;
    Food component1;
    Food component2;
    Food component3;

    public Recipe(String name, Food component1, Food component2, Food component3) {
        this.name = name;
        this.component1 = component1;
        this.component2 = component2;
        this.component3 = component3;
    }

    public String getName() {
        return name;
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
        return "Recipe: " + name + " { " + component1.toString() + " , " + component2.toString() + " , " + component3.toString() + " } "; //To change body of generated methods, choose Tools | Templates.
    }
    
    public JsonObject getJson(){
        JsonObject storage = new JsonObject();
        
        JsonObject foodItems = new JsonObject();
       
        foodItems.addProperty(component1.getClass().getSimpleName().toLowerCase(), component1.getQuantity());
        foodItems.addProperty(component2.getClass().getSimpleName().toLowerCase(), component2.getQuantity());
        foodItems.addProperty(component3.getClass().getSimpleName().toLowerCase(), component3.getQuantity());
        
        storage.add("Recipe", foodItems);
        return storage;
    }
    
}
