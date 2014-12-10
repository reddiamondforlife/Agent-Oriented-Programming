/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage;

import aopdomotics.Helper;
import aopdomotics.house.HouseAgent;
import aopdomotics.storage.food.dairy.Butter;
import aopdomotics.storage.food.dairy.Cheese;
import aopdomotics.storage.food.dairy.Milk;
import aopdomotics.storage.food.fruit.Apple;
import aopdomotics.storage.food.fruit.Lemon;
import aopdomotics.storage.food.fruit.Strawberry;
import aopdomotics.storage.food.grains.Bread;
import aopdomotics.storage.food.grains.Corn;
import aopdomotics.storage.food.grains.Rice;
import aopdomotics.storage.food.meat.Bacon;
import aopdomotics.storage.food.meat.Beef;
import aopdomotics.storage.food.meat.Chicken;
import aopdomotics.storage.food.vegetable.Onion;
import aopdomotics.storage.food.vegetable.Potato;
import aopdomotics.storage.food.vegetable.Tomato;
import com.google.gson.JsonObject;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daan
 */
public class RecipeAgent extends Agent {
    
    protected AID storageAgent;
    
    protected AID personAgent;
    
    protected void setup() {
        // Printout a welcome message
        System.out.println("Hello! Recipe-agent " + getAID().getName() + " is ready.");
        Helper.registerAgent(this, getAID(), "recipe-agent", "JADE-Recipe-Agent");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(HouseAgent.class.getName()).log(Level.SEVERE, null, ex);
        }

        //recipeAgent     = Helper.getAgent(this, "recipe");
        //supermarketAgent = Helper.getAgent(this, "supermarket");
        
        addBehaviour(new UserCFPHandler());
        addBehaviour(new StorageInformHandler());
        addBehaviour(new UserRespondsHandler());
        
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

    //Handlers
    // inform
        // subtract of storage
            //recipe accepted
        // add to storage
            //supermarket buy
    
    // request
        
    
   
    
    private class UserCFPHandler extends CyclicBehaviour {
        
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP); 
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it 
                System.out.println("Receive user request for recipe");
                String content = msg.getContent();
                //parse json 
                
                //prepare recipes 
            } else {
                block();
            }
        }

    }
    
    private class StorageInformHandler extends CyclicBehaviour {
        
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM); 
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it 
                System.out.println("Storage inform about storage items");
                String content = msg.getContent();
                //parse json 
                
                //prepare recipes 
            } else {
                block();
            }
        }

    }
    
    private class UserRespondsHandler extends CyclicBehaviour {
        
        @Override
        public void action() {
            MessageTemplate ap = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL); 
            ACLMessage amsg = myAgent.receive(ap);
            MessageTemplate rp = MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL); 
            ACLMessage rsg = myAgent.receive(rp);
            if (amsg != null) {
                // Message received. Process it 
                System.out.println("User accepted receipe");
                String content = amsg.getContent();
                
            } else if (rsg != null) {
                // Message received. Process it 
                System.out.println("User refused receipe");
                String content = rsg.getContent();
                
            } else {
                block();
            }
        }

    }
}
