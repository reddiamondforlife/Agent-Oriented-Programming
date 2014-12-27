/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage;

import aopdomotics.storage.recipe.Recipe;
import aopdomotics.storage.food.Food;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;

/**
 *
 * @author Daan
 */
public class FoodStorage {
    ArrayList<Food> foods = new ArrayList<>();

    public FoodStorage() {
    }

    
    public ArrayList<Food> getFoods() {
        return foods;
    }
    
    public void addFood(Food food){
        //Add food items, and set new quantity in storage
        boolean found = false;
        for(Food item : foods){
            if(item.getClass() == food.getClass()){
                found = true;
                System.out.println("Added to old food type");
                
                item.setQuantity(item.getQuantity()+food.getQuantity());
                break;
            }
        }
        if(!found){
            System.out.println("Created new food type");
            foods.add(food);
        }
    }
    
    public void addFood(GroceryBill bill){
        //Add items after bought items from the grocery bill
        for(Food item : bill.getFoods()){
            System.out.println(item.toString() + " from bill added to storage");
            //set the quantity bought from the amount we wanted to buy, so it is easier to use the addFood(food) method
            item.setQuantity(item.buyQuantity());
            addFood(item);
        }
        System.out.println("Bill added to food storage");
        System.out.println("Food Storage :\n"+getStorage().toString());
    }

    public void removeFood(Recipe recipe){
        //Get recipe and remove all three items from the storage
        Food[] consumed = new Food[3];
        consumed[0] = recipe.getComponent1();
        consumed[1] = recipe.getComponent2();
        consumed[2] = recipe.getComponent3();
        
        //For every consumed recipe item .
        for(Food consumedItem : consumed){
            // . locate the correct food component ..
            for(Food item : foods){
                // .. and if found ...
                if(item.getClass() == consumedItem.getClass()){
                    // ... substract the current quantity with the consumed quantity
                    System.out.println("Removed " + consumedItem.getQuantity() + " from " + consumedItem.getClass().getSimpleName());
                    item.setQuantity(item.getQuantity()-consumedItem.getQuantity());
                }
            }
        }
    }
    
    public GroceryBill checkStorageRebuy(){
        System.out.println("for the next time my shopping list needs to contain");
        GroceryBill bill = new GroceryBill();
        for(Food item : foods){
            if(item.getQuantity() <= item.getLowerLimit()){
                System.out.println("I need to rebuy " + item.getClass().getSimpleName());
                bill.getFoods().add(item);                
            }
        }
        return bill;
    }
    
    public JsonObject getStorage(){
        JsonObject storage = new JsonObject();
        
        JsonArray foodItems = new JsonArray();
        for(Food item : foods){
            JsonObject jsonItem = new JsonObject();
            jsonItem.addProperty(item.getClass().getSimpleName(), item.getQuantity());
            foodItems.add(jsonItem);
        }
        
        storage.add("Storage", foodItems);
        return storage;
    }
    
    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
