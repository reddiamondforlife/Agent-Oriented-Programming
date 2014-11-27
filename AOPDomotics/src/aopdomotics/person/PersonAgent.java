/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.person;

import aopdomotics.Helper;
import aopdomotics.house.HouseAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daan
 */
public class PersonAgent extends Agent {

    protected static AID houseAgent;
    
    protected static AID multimediaAgent;

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
}
