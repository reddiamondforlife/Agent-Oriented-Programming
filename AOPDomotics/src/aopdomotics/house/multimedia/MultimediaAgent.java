/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.house.multimedia;

import aopdomotics.Helper;
import aopdomotics.house.HouseAgent;
import static aopdomotics.storage.StorageAgent.bill;
import aopdomotics.storage.food.FoodJsonDecoder;
import aopdomotics.storage.recipe.Recipe;
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
        
        musicPlayer = new MusicPlayer(false);
        
        addBehaviour(new InformHandler());
        addBehaviour(new MusicListenerHandler());
    }

    protected void adjustMusicGenre() {
        
    } 

     
    private class InformHandler extends CyclicBehaviour {
        
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchConversationId("multimedia-stress-status"); 
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it 
                //System.out.println("Multimedia got an inform message");
                String content = msg.getContent();
                if(content.startsWith("Stress: ")){
                    int stressLevel = Integer.parseInt(content.substring(content.indexOf("Stress: ")+ "Stress: ".length()));
                    //System.out.println("MULTI: Found stress level " + stressLevel);
                    musicPlayer.setMood(stressLevel);
                } else {
                    System.out.println("Unknown message");
                }
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
   
    
    private class MusicListenerHandler extends CyclicBehaviour {

        @Override
        public void action() {
            
            MessageTemplate mt = MessageTemplate.MatchConversationId("music-status");
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it 
                //System.out.println("Music listener");
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
