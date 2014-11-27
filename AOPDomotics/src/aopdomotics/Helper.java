/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daan
 */
public class Helper {

    public static boolean registerAgent(Agent agent, AID agentName, String serviceType, String serviceName) { //this , getAID()
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(agentName);

        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceType);
        sd.setName(serviceName);
        dfd.addServices(sd);

        try {
            DFService.register(agent, dfd);
            return true;
        } catch (FIPAException fe) {
            fe.printStackTrace();
            return false;
        }

    }

    public static AID getAgent(Agent agent, String name) {
        while(true){
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType(name+"-agent");
            template.addServices(sd);
            DFAgentDescription[] result;
            try {
                result = DFService.search(agent, template);
                System.out.println("Added " + name + " Agent AID");
                if(result.length == 0){
                    Thread.sleep(1000);
                    continue;
                }
                return result[0].getName();

            } catch (FIPAException ex) {
                System.out.println("FIPA ERROR");
            } catch (InterruptedException ex) {
                System.out.println("DELAY ERROR");
            }
            return null;
        }
    }
    
}
