/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.house.multimedia;

import aopdomotics.Helper;
import aopdomotics.house.HouseAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daan
 */
public class MultimediaAgent extends HouseAgent {
    
    private MusicPlayer musicPlayer;

    protected void setup() {
        // Printout a welcome message
        System.out.println("Hello! Multimedia-agent " + getAID().getName() + " is ready.");
        Helper.registerAgent(this, getAID(), "multimedia-agent", "JADE-Multimedia-Agent");
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MultimediaAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        musicPlayer = new MusicPlayer(false);
        
        addBehaviour(new StressInformHandler());
        addBehaviour(new MusicListenerHandler());
       
    }
     
    /**
     * Wait for other agents to notify new stress level which influences music category
     */
    private class StressInformHandler extends CyclicBehaviour {
        
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchConversationId("stress-notify");
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it 
                //System.out.println("Multimedia got an inform message");
                String content = msg.getContent();
                int stressLevel = Integer.parseInt(content);
                //System.out.println("MULTI: Found stress level " + stressLevel);
                musicPlayer.setMusic(stressLevel);
            } else {
                block();
            }
        }

    }
    
    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("Multimedia-agent" + getAID().getName() + " terminating.");
    }
   
    /**
     * Wait for turn on and off messages to switch music on and off due to location/person day routine
     */
    private class MusicListenerHandler extends CyclicBehaviour {

        @Override
        public void action() {
            
            MessageTemplate mt = MessageTemplate.MatchConversationId("music-status");
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it 
                String musicMessage = msg.getContent();
                if(musicMessage.equals("on")){
                    musicPlayer.turnOn();
                } else if(musicMessage.equals("off")){
                    musicPlayer.turnOff();
                }
                
            } else {
                block();
            }
        }

    }
}
