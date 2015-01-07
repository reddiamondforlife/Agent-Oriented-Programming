/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.person;

import aopdomotics.Helper;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Daan
 */
public class PhysicalAgent extends PersonAgent {
    
    private HeartbeatSensor heartBeat;

    protected void setup() {
        // Printout a welcome message
        System.out.println("Hello! Physical-agent " + getAID().getName() + " is ready.");
        Helper.registerAgent(this, getAID(), "physical-agent", "JADE-Physical-Agent");

        
        
        /*addBehaviour(new TickerBehaviour(this, 1000) {
            protected void onTick() {
                notifyMultimedia("Stress",heartBeat.getStressLevel());
            }
        });*/

    }
    
    protected void notifyMultimedia(String name, int variable) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        //System.out.println("Sending stress to " + super.houseAgent);
        msg.addReceiver(super.multimediaAgent);
        msg.setConversationId("multimedia-stress-status");
        msg.setContent(name+": "+variable);  
        send(msg);
    }

    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("Psychological-agent" + getAID().getName() + " terminating.");
    }
}
