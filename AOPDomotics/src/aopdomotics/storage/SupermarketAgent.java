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
import aopdomotics.storage.food.meat.Bacon;
import aopdomotics.storage.food.meat.Beef;
import aopdomotics.storage.food.meat.Chicken;
import aopdomotics.storage.food.vegetable.Onion;
import aopdomotics.storage.food.vegetable.Potato;
import aopdomotics.storage.food.vegetable.Tomato;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daan
 */
public class SupermarketAgent extends Agent {
    static int num = 0;
    public ArrayList<Food> catalogue;
    
    // Put agent initializations here
    protected void setup() {
        System.out.println("Hello! Supermarket-agent " + (num) + getAID().getName() + " is ready.");
        Helper.registerAgent(this, getAID(), "supermarket-agent", "JADE-Supermarket-Agent"+(num));
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SupermarketAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Create the catalogue
        catalogue = new ArrayList<>();
        
        //Create random prices for all the prices for each supermarket agent upon start
        Random random = new Random();
        //grains
        catalogue.add(new Bread((float) (random.nextFloat()+0.75)));
        catalogue.add(new Corn((float) (random.nextFloat()+0.5)));
        catalogue.add(new Rice((float) (random.nextFloat()+1.0)));
        //dairy
        catalogue.add(new Butter((float) (random.nextFloat()+1.0)));
        catalogue.add(new Cheese((float) (random.nextFloat()+2.5)));
        catalogue.add(new Milk((float) (random.nextFloat()+0.6)));
        //fruit
        catalogue.add(new Apple((float) (random.nextFloat()+1.9)));
        catalogue.add(new Lemon((float) (random.nextFloat()+0.5)));
        catalogue.add(new Strawberry((float) (random.nextFloat()+1.0)));
        //meat
        catalogue.add(new Bacon((float) (random.nextFloat()+2.0)));
        catalogue.add(new Beef((float) (random.nextFloat()+5.0)));
        catalogue.add(new Chicken((float) (random.nextFloat()+2.5)));
        //vegetable
        catalogue.add(new Onion((float) (random.nextFloat()+0.5)));
        catalogue.add(new Tomato((float) (random.nextFloat()+1.0)));
        catalogue.add(new Potato((float) (random.nextFloat()+2.0)));

        num++;
        // Create and show the GUI
        // Add the behaviour serving requests for offer from buyer agents
        addBehaviour(new OfferRequestsServer());
        // Add the behaviour serving purchase orders from buyer agents
        addBehaviour(new PurchaseOrdersServer());
    }

    // Put agent clean-up operations here
    protected void takeDown() {
        // Printout a dismissal message
        System.out.println("Supermarket - agent " + getAID().getName() + " terminating.");
    }

    /**
     * This is invoked by the GUI when the user adds a new book for sale
     * @param title
     * @param price
     */
    public void updateCatalogue(final String title, final int price) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                //catalogue.put(title, price);
            }
        });
    }


    private class OfferRequestsServer extends CyclicBehaviour {

        @Override
        public void action() {
            
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.CFP), MessageTemplate.MatchConversationId("supermarket-trade"));
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                System.out.println("received offer request, sending offer");
                // Message received. Process it
                String billString = msg.getContent();
                
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.PROPOSE);
                //propose price using decoded bill and random prices, format using US format to be able to decode the price using a comma instead of a dot. (needed for String to float)
                reply.setContent(String.format(Locale.US, "%.2f", FoodJsonDecoder.decodeBill(billString, catalogue)));
                myAgent.send(reply);
            }
        }
    }
    
    private class PurchaseOrdersServer extends CyclicBehaviour {

        @Override
        public void action() {
            
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL), MessageTemplate.MatchConversationId("supermarket-trade-accept"));
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                System.out.println("Received a purchase order confimation, sending inform message");
                // ACCEPT_PROPOSAL Message received. Process it
                ACLMessage reply = msg.createReply();
                //inform that we got the message.
                reply.setPerformative(ACLMessage.INFORM);
                reply.setContent("OK");
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }    
}
