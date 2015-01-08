/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.house.multimedia;

import aopdomotics.Helper;
import aopdomotics.house.HouseAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

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
        
        musicPlayer = new MusicPlayer();
        
        addBehaviour(new StressInformHandler());

    }

    protected void adjustMusicGenre() {
        
    } 

     
    private class StressInformHandler extends CyclicBehaviour {
        
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchConversationId("stress-notify");
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it 
                System.out.println("Multimedia got an inform message");
                String content = msg.getContent();
              
                int stressLevel = Integer.parseInt(content);
                System.out.println("MULTI: Found stress level " + stressLevel);
                musicPlayer.setMood(stressLevel);
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
   
}
