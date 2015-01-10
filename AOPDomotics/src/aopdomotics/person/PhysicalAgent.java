/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.person;

import aopdomotics.Helper;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daan
 */
public class PhysicalAgent extends PersonAgent {
    
    private HeartbeatSensor heartBeat;
    private BloodPressure bloodPressure;

    protected void setup() {
        // Printout a welcome message
        System.out.println("Hello! Physical-agent " + getAID().getName() + " is ready.");
        Helper.registerAgent(this, getAID(), "physical-agent", "JADE-Physical-Agent");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(PhysicalAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        addBehaviour(new TickerBehaviour(this, 1000) {
            protected void onTick() {
                notifyMultimedia("stress",(int)((double)heartBeat.getStressLevel()/(double)bloodPressure.getBloodpressure()*100));

            }
        });

    }
    
    /**
     * Notify multimedia agent on a variable name and a variable
     * @param name
     * @param variable 
     */
    protected void notifyMultimedia(String name, int variable) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        //System.out.println("Sending stress to " + super.houseAgent);
        msg.addReceiver(super.multimediaAgent);
        msg.setConversationId(name+"-notify");
        msg.setContent(String.valueOf(variable));  
        send(msg);
    }

    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("Physical-agent" + getAID().getName() + " terminating.");
    }
}
