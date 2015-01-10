/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage;

import aopdomotics.Helper;
import aopdomotics.house.HouseAgent;
import aopdomotics.storage.food.Food;
import aopdomotics.storage.food.FoodJsonDecoder;
import aopdomotics.storage.food.dairy.Butter;
import aopdomotics.storage.food.dairy.Cheese;
import aopdomotics.storage.food.dairy.Milk;
import aopdomotics.storage.food.fruit.Apple;
import aopdomotics.storage.food.fruit.Lemon;
import aopdomotics.storage.food.fruit.Strawberry;
import aopdomotics.storage.food.grains.Bread;
import aopdomotics.storage.food.grains.Corn;
import aopdomotics.storage.food.grains.Rice;
import aopdomotics.storage.food.meat.Beef;
import aopdomotics.storage.food.meat.Chicken;
import aopdomotics.storage.food.vegetable.Onion;
import aopdomotics.storage.food.vegetable.Potato;
import aopdomotics.storage.food.vegetable.Tomato;
import aopdomotics.storage.recipe.Recipe;
import aopdomotics.storage.recipe.RecipeAnalyzer;
import aopdomotics.storage.recipe.RecipePreference;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daan
 */
public class RecipeAgent extends Agent {

    protected AID storageAgent;

    protected AID personAgent;

    private RecipeAnalyzer analyzer;

    Recipe selectedRecipe;

    ArrayList<Food> catalogue;
    
    protected void setup() {
        // Printout a welcome message
        System.out.println("Hello! Recipe-agent " + getAID().getName() + " is ready.");
        Helper.registerAgent(this, getAID(), "recipe-agent", "JADE-Recipe-Agent");

        catalogue = new ArrayList<>();
        
        //Create common recipes with three types of food
        analyzer = new RecipeAnalyzer();
        analyzer.recipes.add(new RecipePreference(new Recipe("Rice with chicken and tomato", new Rice(3, 0, 0), new Chicken(2, 0, 0), new Tomato(1, 0, 0)), 0));
        analyzer.recipes.add(new RecipePreference(new Recipe("Potato, beef and corn", new Potato(3, 0, 0), new Beef(2, 0, 0), new Corn(1, 0, 0)), 0));
        analyzer.recipes.add(new RecipePreference(new Recipe("Breakfast", new Bread(3, 0, 0), new Butter(2, 0, 0), new Milk(1, 0, 0)), 0));
        analyzer.recipes.add(new RecipePreference(new Recipe("Fruitsalad", new Strawberry(3, 0, 0), new Apple(2, 0, 0), new Tomato(1, 0, 0)), 0));
        analyzer.recipes.add(new RecipePreference(new Recipe("Randoms", new Onion(3, 0, 0), new Lemon(2, 0, 0), new Cheese(1, 0, 0)), 0));

        //Wait a second, so all other agents are certainly registered.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(HouseAgent.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Get the AID for the storage agent
        storageAgent = Helper.getAgent(this, "storage");

        addBehaviour(new UserCFPHandler());         //Handle requests for recipes from the person
        addBehaviour(new RecipeAcceptServer());     //Handle the accept message from the person
        addBehaviour(new StorageInformHandler());   //Inform storage agent that an recipe has been eaten, so the food items should be deduced from storage.

    }

    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("Recipe-agent" + getAID().getName() + " terminating.");
    }

    /**
     * Wait for CFP to send available recipe to person back.
     */
    private class UserCFPHandler extends CyclicBehaviour {

        @Override
        public void action() {
            //Filter only on Call for Proposal messages
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it 
                System.out.println("Received user request for recipe");

                ACLMessage reply = msg.createReply();
                // Create a proposal for a recipe that the person could eat.
                reply.setPerformative(ACLMessage.PROPOSE);
                //select random recipe
                Random r = new Random();
                int randomNum = r.nextInt(analyzer.recipes.size()); //get index of a random recipe
                
                selectedRecipe = analyzer.getAvailableRecipes().get(randomNum).recipe; //get the random recipe
                System.out.println("Selected Recipe: " + selectedRecipe.toString());
                //Send the name of the recipe
                reply.setContent(selectedRecipe.getName());
                myAgent.send(reply);
            } else {
                block();
            }
        }

    }

    /**
     * Wait for accepting proposal message from person , and inform storage agent of accepted recipe
     */
    private class RecipeAcceptServer extends CyclicBehaviour {

        @Override
        public void action() {
            //Wait for an message with and, id = recipe-request and performative is accept proposal
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL), MessageTemplate.MatchConversationId("recipe-request"));
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // ACCEPT_PROPOSAL Message received. Process it
                //System.out.println("Message received and process it");
                String title = msg.getContent();
                ACLMessage reply = msg.createReply();
                //When the person want the recipe, we inform them that he accepted the proposal
                reply.setPerformative(ACLMessage.INFORM);
                myAgent.send(reply);

                //send to storage to inform recipe is consumed
                //System.out.println("sending inform to storage");
                ACLMessage informStorage = new ACLMessage(ACLMessage.INFORM);
                informStorage.addReceiver(storageAgent);
                //give a json representation of the recipe, including the individual food items and quality of the items consumed
                informStorage.setContent(selectedRecipe.getJson().toString());
                informStorage.setConversationId("recipe-inform");
                informStorage.setReplyWith("inform" + System.currentTimeMillis()); // Unique value
                myAgent.send(informStorage);
                
                
            } else {
                block();
            }
        }
    }

    /**
     * Wait for storage update inform message which grants new information about available food items and calculate which 
     */
    private class StorageInformHandler extends CyclicBehaviour {

        @Override
        public void action() {
            //Wait for an inform message from storage about new storage levels
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchConversationId("storage-update"));
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it 
                //System.out.println("Storage inform about storage items");
                String storageUpdate = msg.getContent();
                //System.out.println("Storage inform : " + content);
                //parse json 
                
                catalogue = FoodJsonDecoder.decodeStorage(storageUpdate);
                //prepare recipes 
                analyzer.inventoryRecipes(catalogue);
                
            } else {
                block();
            }
        }

    }
}
