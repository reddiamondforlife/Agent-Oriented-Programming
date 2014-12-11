 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage;

import aopdomotics.Helper;
import aopdomotics.storage.food.Food;
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

/**
 *
 * @author Daan
 */
public class SupermarketAgent extends Agent {
    static int num = 0;
    public ArrayList<Food> catalogue;
    
    // Put agent initializations here
    protected void setup() {
        // Create the catalogue
        catalogue = new ArrayList<>();
        
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
        
        System.out.println("Catelogue 2 item " + catalogue.get(2).toString());
        System.out.println("Catalogue 2 price " + catalogue.get(2).getPrice());
        
        System.out.println("Hello! Supermarket-agent " + (num) + getAID().getName() + " is ready.");
        Helper.registerAgent(this, getAID(), "supermarket-agent", "JADE-Supermarket-Agent"+(num));
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

    /**
     * Inner class OfferRequestsServer. This is the behaviour used by
     * Book-seller agents to serve incoming requests for offer from buyer
     * agents. If the requested book is in the local catalogue the seller agent
     * replies with a PROPOSE message specifying the price. Otherwise a REFUSE
     * message is sent back.
     */
    private class OfferRequestsServer extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it
                System.out.println("Message received process it offer requests server");
                String billString = msg.getContent();
                System.out.println("Bill: " + billString);
                
                ACLMessage reply = msg.createReply();
                //Integer price = (Integer) catalogue.get(title);
                    // The requested book is available for sale. Reply with the price
                reply.setPerformative(ACLMessage.PROPOSE);
                Random r = new Random();
                reply.setContent(String.format(Locale.US, "%.2f", decodeBill(billString)));
                myAgent.send(reply);
            }
        }
    } // End of inner class OfferRequestsServer

    private float decodeBill(String billJson){

        JsonElement jelement = new JsonParser().parse(billJson);
        JsonObject json = jelement.getAsJsonObject();

        JsonElement gpsElement = json.get("Bill");
        System.out.println("Bill JSON " + gpsElement.toString());

        Gson gson = new GsonBuilder().create();
        BillDecoder billdecoder = gson.fromJson(gpsElement, BillDecoder.class);
        System.out.println("billdecoder rice " + billdecoder.rice);
        System.out.println("Price proposing " + billdecoder.billPrice(catalogue));
        
        return billdecoder.billPrice(catalogue);
    }
    
    /**
     * Inner class PurchaseOrdersServer. This is the behaviour used by
     * Book-seller agents to serve incoming offer acceptances (i.e. purchase
     * orders) from buyer agents. The seller agent removes the purchased book
     * from its catalogue and replies with an INFORM message to notify the buyer
     * that the purchase has been sucesfully completed.
     */
    private class PurchaseOrdersServer extends CyclicBehaviour {

        @Override
        public void action() {
            
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // ACCEPT_PROPOSAL Message received. Process it
                System.out.println("Message received and process it");
                String title = msg.getContent();
                ACLMessage reply = msg.createReply();
                //Integer number = (Integer) catalogue.remove(title);

                reply.setPerformative(ACLMessage.INFORM);
                System.out.println(title + " <- title | message sender -> " + msg.getSender().getName());

                myAgent.send(reply);
            } else {
                block();
            }
        }
    }  // End of inner class OfferRequestsServer
    
    private class BillDecoder{
        int bread = 0;
        int corn = 0;
        int rice = 0;
        
        int butter = 0;
        int cheese = 0;
        int milk = 0;
        
        int apple = 0;
        int lemon = 0;
        int strawberry = 0;
        
        int bacon = 0;
        int beef = 0;
        int chicken = 0;
        
        int onion = 0;
        int tomato = 0;
        int potato = 0;
        
        public float billPrice(ArrayList<Food> magazinList){
            float totalPrice = 0.0f;
            System.out.println("Getting bill price");
          
            totalPrice += bread * magazinList.get(0).getPrice(); //catalogue 2 is rice
            totalPrice += corn * magazinList.get(1).getPrice(); //catalogue 2 is rice
            totalPrice += rice * magazinList.get(2).getPrice(); //catalogue 2 is rice
            
            totalPrice += butter * magazinList.get(3).getPrice(); //catalogue 2 is rice
            totalPrice += cheese * magazinList.get(4).getPrice(); //catalogue 2 is rice
            totalPrice += milk * magazinList.get(5).getPrice(); //catalogue 2 is rice
            
            totalPrice += apple * magazinList.get(6).getPrice(); //catalogue 2 is rice
            totalPrice += lemon * magazinList.get(7).getPrice(); //catalogue 2 is rice
            totalPrice += strawberry * magazinList.get(8).getPrice(); //catalogue 2 is rice
            
            totalPrice += bacon * magazinList.get(9).getPrice(); //catalogue 2 is rice
            totalPrice += beef * magazinList.get(10).getPrice(); //catalogue 2 is rice
            totalPrice += chicken * magazinList.get(11).getPrice(); //catalogue 2 is rice
            
            totalPrice += onion * magazinList.get(12).getPrice(); //catalogue 2 is rice
            totalPrice += tomato * magazinList.get(13).getPrice(); //catalogue 2 is rice
            totalPrice += potato * magazinList.get(14).getPrice(); //catalogue 2 is rice
                       
            return totalPrice;
        }
    }
}
