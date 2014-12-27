/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage;

import aopdomotics.storage.food.Food;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;

/**
 *
 * @author Daan
 */
public class GroceryBill {
    
    ArrayList<Food> foods = new ArrayList<>();

    public GroceryBill() {
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public JsonObject getJson(){
        //Convert food items to json
        JsonObject storage = new JsonObject();
        
        JsonObject foodItems = new JsonObject();
        for(Food item : foods){
            JsonElement jsonItem = new JsonObject();
            foodItems.addProperty(item.getClass().getSimpleName().toLowerCase(), item.getQuantity());
        }
        
        storage.add("Bill", foodItems);
        return storage;
    }
    
    @Override
    public String toString() {
        return foods.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
