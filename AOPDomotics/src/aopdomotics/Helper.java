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
public class Helper
{

    public static boolean registerAgent(Agent agent, AID agentName, String serviceType, String serviceName)
    { //this , getAID()
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(agentName);

        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceType);
        sd.setName(serviceName);
        dfd.addServices(sd);

        try
        {
            DFService.register(agent, dfd);
            return true;
        } catch (FIPAException fe)
        {
            fe.printStackTrace();
            return false;
        }

    }

    public static AID getAgent(Agent agent, String name)
    {
        while (true)
        {
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType(name + "-agent");
            template.addServices(sd);
            DFAgentDescription[] result;
            try
            {
                result = DFService.search(agent, template);
                
                if(result.length == 0){
                    return null;
                }
                System.out.println("Added " + name + " Agent AID");
                
                return result[0].getName();

            } catch (FIPAException ex)
            {
                System.out.println("FIPA ERROR");
            }
            return null;
        }
    }
    
    public static AID[] getAgents(Agent agent, String name) {
        while(true){
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType(name+"-agent");
            template.addServices(sd);
            DFAgentDescription[] result;
            
            try {
                result = DFService.search(agent, template);
                
                if(result.length == 0){
                    return null;
                }
                System.out.println("Added " + name + " Agent AID");
                
                //Copy the names of the results to an AID array and return this array.
                AID[] agents = new AID[result.length];
                for(int i = 0; i < result.length; i++){
                   agents[i] = result[i].getName();
                }
                
                return agents;

            } catch (FIPAException ex) {
                System.out.println("FIPA ERROR");

            }
            return null;
        }
    }

}
