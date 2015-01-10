/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.house;

import aopdomotics.Helper;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Daan
 */
public class HouseAgent extends Agent
{

    protected AID personAgent;

    protected AID multimediaAgent;

    protected AID airQualityAgent;

    protected void setup()
    {
        // Printout a welcome message
        System.out.println("Hello! House-agent " + getAID().getName() + " is ready.");
        Helper.registerAgent(this, getAID(), "house-agent", "JADE-House-Agent");

        try
        {
            Thread.sleep(1000); //wait a sec for all agents to register
        } 
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }

        //get agents
        personAgent = Helper.getAgent(this, "person");
        multimediaAgent = Helper.getAgent(this, "multimedia");
        airQualityAgent = Helper.getAgent(this, "airquality");
        
        //Set comfort level for air sensor using ACL message in a one shot behaviour
        addBehaviour(new AirSensorComfortHandler(this, 20.0f));

    }

    protected void takeDown()
    {
        // Deregister from the yellow pages
        try
        {
            DFService.deregister(this);
        } catch (FIPAException fe)
        {
            fe.printStackTrace();
        }
        System.out.println("House-agent" + getAID().getName() + " terminating.");
    }    
    
    /**
     * Send a message to air quality agent to update the comfort level
     */
    private class AirSensorComfortHandler extends OneShotBehaviour{

        float preference;
        
        public AirSensorComfortHandler(Agent a, float preference) {
            super(a);
            this.preference = preference;
        }

        @Override
        public void action() {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            //System.out.println("Sending stress to " + super.houseAgent);
            msg.addReceiver(airQualityAgent);
            msg.setConversationId("comfort-update");
            msg.setContent(String.valueOf(preference));  
            send(msg);
        }
       
    }
    
}
