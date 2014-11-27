/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.house;

import aopdomotics.Helper;
import aopdomotics.person.PersonAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
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
public class HouseAgent extends Agent {

    protected AID personAgent;
    
    protected AID multimediaAgent;

    protected void setup() {
        // Printout a welcome message
        System.out.println("Hello! House-agent " + getAID().getName() + " is ready.");
        Helper.registerAgent(this, getAID(), "house-agent", "JADE-House-Agent");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(HouseAgent.class.getName()).log(Level.SEVERE, null, ex);
        }

        personAgent     = Helper.getAgent(this, "person");
        multimediaAgent = Helper.getAgent(this, "multimedia");
        
        addBehaviour(new InformHandler());
        
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

    private class InformHandler extends CyclicBehaviour {
        
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM); 
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it 
                System.out.println("House got an inform message");
                String content = msg.getContent();
                if(content.startsWith("Stress: ")){
                    int stressLevel = Integer.parseInt(content.substring(content.indexOf("Stress: ")+ "Stress: ".length()));
                    System.out.println("HOUSE: Found stress level " + stressLevel);
                    
                } else {
                    System.out.println("Unknown message");
                }
            } else {
                block();
            }
        }

    }
}
