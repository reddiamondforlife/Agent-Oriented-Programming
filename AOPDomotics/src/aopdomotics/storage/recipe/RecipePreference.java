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
public class RecipePreference {

    public Recipe recipe;
    public int karma; //choice preference
    public boolean enoughResources;

    public RecipePreference(Recipe recipe, int karma) {
        this.recipe = recipe;
        this.karma = karma;
        enoughResources = false;
    }

    public void checkInventory(ArrayList<Food> foods) {
        Food item1 = null;
        Food item2 = null;
        Food item3 = null;
        for (Food food : foods) {
            //When class is equal to recipe component and there is enough quanity left to 'make' it, then set item as food
            if (food.getClass().equals(recipe.getComponent1().getClass())
                    && food.getQuantity() >= recipe.getComponent1().getQuantity()
                    ) {
                item1 = food;
            } else if (food.getClass().equals(recipe.getComponent2().getClass())
                    && food.getQuantity() >= recipe.getComponent2().getQuantity()
                    ) {
                item2 = food;
            } else if (food.getClass().equals(recipe.getComponent3().getClass())
                    && food.getQuantity() >= recipe.getComponent3().getQuantity()
                    ) {
                item3 = food;
            }
        }
        enoughResources = (item1 != null) && (item2 != null) && (item3 != null); //if item 1 or item 2 or item 3 is null, then there are not enough resources.
    }

    @Override
    public String toString() {
        return "Recipe Prefference for " + recipe.toString() + " has a karma of " + karma + " and is " + ((enoughResources) ? ("craftable") : ("NOT available"));
    }

}
