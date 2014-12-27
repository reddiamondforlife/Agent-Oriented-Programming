/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage.recipe;

import aopdomotics.storage.food.Food;
import java.util.ArrayList;

/**
 *
 * @author Daan
 */
public class RecipeAnalyzer {
    
    public ArrayList<RecipePreference> recipes = new ArrayList<>();
    
    public ArrayList<Food> storage = new ArrayList<>();
    
    //priority array
    
    //sorting algorithm

    public RecipeAnalyzer() {
    }
    
    public void inventoryRecipes(ArrayList<Food> foods){
        
        for(RecipePreference recipe : recipes){
            recipe.checkInventory(foods);
        }
    }
    public ArrayList<RecipePreference> getAvailableRecipes(){
        ArrayList<RecipePreference> available = new ArrayList<>();
        
        for(RecipePreference recipe : recipes){
            if(recipe.enoughResources){
                available.add(recipe);
            }
        }
        return available;
    }
}
