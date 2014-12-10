/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage;

import aopdomotics.Helper;
import aopdomotics.storage.food.Food;
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

/**
 *
 * @author Daan
 */
public class SupermarketAgent extends Agent {
    static int num = 0;
    ArrayList<Food> catalogue;
    
    // Put agent initializations here
    protected void setup() {
        // Create the catalogue
        catalogue = new ArrayList<>();
        catalogue.add(new Food(1000, 1000, 100));
        
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
                String title = msg.getContent();
                ACLMessage reply = msg.createReply();
                //Integer price = (Integer) catalogue.get(title);
                    // The requested book is available for sale. Reply with the price
                reply.setPerformative(ACLMessage.PROPOSE);
                //reply.setContent(String.valueOf(price.intValue()));
                myAgent.send(reply);
            }
        }
    } // End of inner class OfferRequestsServer

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
}
