/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage;

import aopdomotics.storage.food.Food;
import com.google.gson.JsonArray;
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
        JsonObject storage = new JsonObject();
        
        JsonArray foodItems = new JsonArray();
        for(Food item : foods){
            JsonObject jsonItem = new JsonObject();
            jsonItem.addProperty(item.getClass().getSimpleName(), item.getQuantity());
            foodItems.add(jsonItem);
        }
        
        storage.add("Bill", foodItems);
        return storage;
    }
    
    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
