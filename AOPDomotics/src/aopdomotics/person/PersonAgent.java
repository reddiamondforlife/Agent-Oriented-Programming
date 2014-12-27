/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.person;

import aopdomotics.Helper;
import aopdomotics.house.HouseAgent;
import aopdomotics.storage.StorageAgent;
import static aopdomotics.storage.StorageAgent.bill;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daan
 */
public class PersonAgent extends Agent {

    protected static AID houseAgent;
    
    protected static AID multimediaAgent;

    protected static AID recipeAgent;
    
    protected void setup() {
        // Printout a welcome message
        System.out.println("Hello! Person-agent " + getAID().getName() + " is ready.");
        Helper.registerAgent(this, getAID(), "person-agent", "JADE-Person-Agent");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(PersonAgent.class.getName()).log(Level.SEVERE, null, ex);
        }

        houseAgent = Helper.getAgent(this,"house");
        multimediaAgent = Helper.getAgent(this,"multimedia");
        
        
        addBehaviour(new TickerBehaviour(this, 7000) {
            @Override
            protected void onTick() {
                System.out.println("On tick .");
                
                System.out.println(". want to eat");
                // Update the list of seller agents
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("recipe-agent");
                template.addServices(sd);
                try {
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    recipeAgent = result[0].getName();
                } catch (FIPAException fe) {
                }
                // Perform the request
                myAgent.addBehaviour(new PersonAgent.RecipeRequestPerformer());
            }
        });
    }
    
    


    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("House-agent" + getAID().getName() + " terminating.");
    }
    
    
    /**
     * Inner class RequestPerformer. This is the behaviour used by Book-buyer
     * agents to request seller agents the target book.
     */
    private class RecipeRequestPerformer extends Behaviour {

        private MessageTemplate mt; // The template to receive replies
        private int step = 0;

        public void action() {
            System.out.println("Action step " + step);
            switch (step) {
                case 0:
                    // Send the cfp to all sellers
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    cfp.addReceiver(recipeAgent);
                    cfp.setContent("food plz");
                    cfp.setConversationId("recipe-request");
                    cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Unique value
                    myAgent.send(cfp);
                    // Prepare the template to get proposals
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("recipe-request"),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                    step = 1;
                    break;
                case 1:
                    // Receive all proposals/refusals from seller agents
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        // Reply received
                        if (reply.getPerformative() == ACLMessage.PROPOSE) {
                            // This is an offer
                            String recipe = reply.getContent();
                            System.out.println("Received an recipe from " + reply.getSender() + " with " + recipe);
                        }
                        
                        step = 2;
                    } else {
                        System.out.println("Got no message");
                        block();
                    }
                    break;
                case 2:
                    // Send the purchase order to the seller that provided the best offer
                    ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    order.addReceiver(recipeAgent);
                    order.setContent(bill.getJson().toString());
                    order.setConversationId("recipe-request");
                    order.setReplyWith("recipeChoice" + System.currentTimeMillis());
                    myAgent.send(order);
                    // Prepare the template to get the purchase order reply
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("recipe-request"),
                            MessageTemplate.MatchInReplyTo(order.getReplyWith()));

                    step = 3;
                    break;
                case 3:
                    // Receive the purchase order reply
                    System.out.println("Waiting for reply");
                    reply = myAgent.receive(mt);
                    if (reply != null) {
                        System.out.println("I did get an inform reply <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                        // Purchase order reply received
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            // Purchase successful. We can terminate
                            System.out.println(bill.getJson().toString() + " successfully chosen.");
                            
                        }
                        step = 4;
                    } else {
                        System.out.println("I didnt get an inform reply -----------------------------------------");
                        block();
                    }
                    break;
            }
        }

        public boolean done() {
            return (step == 4);
        }
    } // End of inner class RequestPerformer

}
