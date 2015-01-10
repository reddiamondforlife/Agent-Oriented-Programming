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
    
    /**
     * Check if recipes are able to be made, using storage information.
     * @param foods 
     */
    public void inventoryRecipes(ArrayList<Food> foods){
        
        for(RecipePreference recipe : recipes){
            recipe.checkInventory(foods);
        }
    }
    
    /**
     * Get available recipes, using the enough resource variable in the recipe variable.
     * @return 
     */
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
