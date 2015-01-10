/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage.food;

import aopdomotics.storage.food.dairy.*;
import aopdomotics.storage.food.fruit.*;
import aopdomotics.storage.food.grains.*;
import aopdomotics.storage.food.meat.*;
import aopdomotics.storage.food.vegetable.*;
import aopdomotics.storage.recipe.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 *
 * @author Daan
 */
public class FoodJsonDecoder {
    
        public int bread = 0;
        public int corn = 0;
        public int rice = 0;
        
        public int butter = 0;
        public int cheese = 0;
        public int milk = 0;
        
        public int apple = 0;
        public int lemon = 0;
        public int strawberry = 0;
        
        public int bacon = 0;
        public int beef = 0;
        public int chicken = 0;
        
        public int onion = 0;
        public int tomato = 0;
        public int potato = 0;
        
        public float billPrice(ArrayList<Food> magazinList){
            float totalPrice = 0.0f;
            System.out.println("Getting bill price");
          
            totalPrice += bread * magazinList.get(0).getPrice(); 
            totalPrice += corn * magazinList.get(1).getPrice(); 
            totalPrice += rice * magazinList.get(2).getPrice(); 
            
            totalPrice += butter * magazinList.get(3).getPrice(); 
            totalPrice += cheese * magazinList.get(4).getPrice();
            totalPrice += milk * magazinList.get(5).getPrice(); 
            
            totalPrice += apple * magazinList.get(6).getPrice();
            totalPrice += lemon * magazinList.get(7).getPrice(); 
            totalPrice += strawberry * magazinList.get(8).getPrice();
            
            totalPrice += bacon * magazinList.get(9).getPrice(); 
            totalPrice += beef * magazinList.get(10).getPrice(); 
            totalPrice += chicken * magazinList.get(11).getPrice(); 
            
            totalPrice += onion * magazinList.get(12).getPrice(); 
            totalPrice += tomato * magazinList.get(13).getPrice(); 
            totalPrice += potato * magazinList.get(14).getPrice(); 
                       
            return totalPrice;
        }
        
         public static float decodeBill(String billJson, ArrayList<Food> magazinList){

            JsonElement jelement = new JsonParser().parse(billJson);
            JsonObject json = jelement.getAsJsonObject();

            JsonElement billElement = json.get("Bill");
            System.out.println("Bill JSON " + billElement.toString());

            Gson gson = new GsonBuilder().create();
            FoodJsonDecoder billdecoder = gson.fromJson(billElement, FoodJsonDecoder.class);

            return billdecoder.billPrice(magazinList);
        }
         
        public static Recipe decodeRecipe(String recipeJson){
            Recipe recipe;
            Food[] component = new Food[3];
            JsonElement jelement = new JsonParser().parse(recipeJson);
            JsonObject json = jelement.getAsJsonObject();

            JsonElement recipeElement = json.get("Recipe");
            System.out.println("Recipe JSON " + recipeElement.toString());

            Gson gson = new GsonBuilder().create();
            FoodJsonDecoder recipedecoder = gson.fromJson(recipeElement, FoodJsonDecoder.class);
            int i = 0;
            for(Food item : recipedecoder.foodList()){
                if(item.quantity > 0){
                    component[i++] = item;
                }
            }
            recipe = new Recipe("eaten", component[0], component[1], component[2]);
            return recipe;
        }
        
        public static ArrayList<Food> decodeStorage(String storageJson){
            System.out.println("Storage Json :  " + storageJson);
            JsonElement jelement = new JsonParser().parse(storageJson);
            JsonObject json = jelement.getAsJsonObject();

            JsonElement storageElement = json.get("Storage");
            System.out.println("Storage JSON " + storageElement.toString());

            Gson gson = new GsonBuilder().create();
            FoodJsonDecoder recipedecoder = gson.fromJson(storageElement, FoodJsonDecoder.class);
            return recipedecoder.foodList();
        }
        
        public ArrayList<Food> foodList(){
            ArrayList<Food> foods = new ArrayList<>();
            foods.add(new Bread(bread,0,0));
            foods.add(new Corn(corn,0,0));
            foods.add(new Rice(rice,0,0));
            
            foods.add(new Butter(butter,0,0));
            foods.add(new Milk(milk,0,0));
            foods.add(new Cheese(cheese,0,0));
            
            foods.add(new Apple(apple,0,0));
            foods.add(new Lemon(lemon,0,0));
            foods.add(new Strawberry(strawberry,0,0));
            
            foods.add(new Bacon(bacon,0,0));
            foods.add(new Beef(beef,0,0));
            foods.add(new Chicken(chicken,0,0));
            
            foods.add(new Onion(onion,0,0));
            foods.add(new Tomato(tomato,0,0));
            foods.add(new Potato(potato,0,0));
            
            return foods;
        }
    }