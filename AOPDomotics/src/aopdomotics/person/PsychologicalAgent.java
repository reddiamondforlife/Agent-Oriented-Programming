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
public class PsychologicalAgent extends PersonAgent {
    
    private HeartbeatSensor heartBeat;

    protected void setup() {
        // Printout a welcome message
        System.out.println("Hello! Psychological-agent " + getAID().getName() + " is ready.");
        Helper.registerAgent(this, getAID(), "psychological-agent", "JADE-Psychological-Agent");

        heartBeat = new HeartbeatSensor();
        
        addBehaviour(new TickerBehaviour(this, 10000) {
            protected void onTick() {
                notifyHouse("Stress",heartBeat.getStressLevel());
            }
        });

    }
    
    protected void notifyHouse(String name, int variable) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        //System.out.println("Sending stress to " + super.houseAgent);
        msg.addReceiver(super.multimediaAgent);
        msg.setLanguage("English");  
        msg.setOntology("Psychological-"+name.toLowerCase()+"-ontology"); 
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
