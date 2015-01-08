/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.storage;

import aopdomotics.storage.recipe.Recipe;
import aopdomotics.Helper;
import aopdomotics.house.HouseAgent;
import aopdomotics.storage.food.FoodJsonDecoder;
import aopdomotics.storage.food.dairy.Butter;
import aopdomotics.storage.food.dairy.Cheese;
import aopdomotics.storage.food.dairy.Milk;
import aopdomotics.storage.food.fruit.Apple;
import aopdomotics.storage.food.fruit.Lemon;
import aopdomotics.storage.food.fruit.Strawberry;
import aopdomotics.storage.food.grains.Bread;
import aopdomotics.storage.food.grains.Corn;
import aopdomotics.storage.food.grains.Rice;
import aopdomotics.storage.food.meat.Bacon;
import aopdomotics.storage.food.meat.Beef;
import aopdomotics.storage.food.meat.Chicken;
import aopdomotics.storage.food.vegetable.Onion;
import aopdomotics.storage.food.vegetable.Potato;
import aopdomotics.storage.food.vegetable.Tomato;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daan
 */
public class StorageAgent extends Agent {

    //keep track of storage
    FoodStorage foodStorage;

    protected AID recipeAgent;

    protected AID[] supermarketAgents;

    public static GroceryBill bill;

    protected void setup() {
        // Printout a welcome message
        System.out.println("Hello! Storage-agent " + getAID().getName() + " is ready.");
        Helper.registerAgent(this, getAID(), "storage-agent", "JADE-Storage-Agent");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(HouseAgent.class.getName()).log(Level.SEVERE, null, ex);
        }

        addBehaviour(new InformHandler());
        //Add all the food items with the respectible current quality | max quatity | lower storage limit
        foodStorage = new FoodStorage();
        //grains
        foodStorage.addFood(new Bread(1, 10, 2));
        foodStorage.addFood(new Corn(1, 10, 1));
        foodStorage.addFood(new Rice(1, 10, 4));
        //dairy
        foodStorage.addFood(new Butter(5, 10, 2));
        foodStorage.addFood(new Cheese(3, 10, 1));
        foodStorage.addFood(new Milk(8, 10, 4));
        //fruit
        foodStorage.addFood(new Apple(5, 10, 2));
        foodStorage.addFood(new Lemon(3, 10, 1));
        foodStorage.addFood(new Strawberry(8, 10, 4));
        //meat
        foodStorage.addFood(new Bacon(5, 10, 2));
        foodStorage.addFood(new Beef(3, 10, 1));
        foodStorage.addFood(new Chicken(8, 10, 4));
        //vegetable
        foodStorage.addFood(new Onion(5, 10, 2));
        foodStorage.addFood(new Tomato(3, 10, 1));
        foodStorage.addFood(new Potato(8, 10, 4));

        System.out.println("Food Storage :\n" + foodStorage.getStorage().toString());

        bill = foodStorage.checkStorageRebuy();

        addBehaviour(new TickerBehaviour(this, 5000) {
            protected void onTick() {
                if (bill.foods.isEmpty()) {
                    //Dont have to buy items because the bill is empty/are no items on the lit
                    return;
                }
                System.out.println("Bill is available and I need to buy " + bill.toString());
                // Update the list of seller agents
                supermarketAgents = Helper.getAgents(myAgent, "supermarket");
                // Perform the request
                myAgent.addBehaviour(new RequestBillPerformer());
            }
        });

        storageInformRecipe(this);

    }

    public void storageInformRecipe(Agent myAgent) {
        System.out.println("On tick .");
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("recipe-agent");
        template.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            recipeAgent = result[0].getName();
        } catch (FIPAException fe) {
        }
        // Perform the request

        ACLMessage storageInform = new ACLMessage(ACLMessage.INFORM);
        storageInform.addReceiver(recipeAgent);
        storageInform.setContent(foodStorage.getStorage().toString());
        storageInform.setConversationId("storage-update");
        myAgent.send(storageInform);
        
    }

    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("Storage-agent" + getAID().getName() + " terminating.");
    }

    private class InformHandler extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchConversationId("recipe-inform");
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Message received. Process it 
                System.out.println("Storage got an inform message probably to decrease storage");
                String recipeString = msg.getContent();
                System.out.println("content: " + recipeString);
                //parse json 
                Recipe recipe = FoodJsonDecoder.decodeRecipe(recipeString);
                foodStorage.removeFood(recipe);
                bill = foodStorage.checkStorageRebuy();
                //decrease from storage.
            } else {
                block();
            }
        }

    }

    /**
     * Inner class RequestPerformer. This is the behaviour used by Book-buyer
     * agents to request seller agents the target book.
     */
    private class RequestBillPerformer extends Behaviour {

        private AID bestSeller; // The agent who provides the best offer
        private float bestPrice; // The best offered price
        private int repliesCnt = 0; // The counter of replies from seller agents
        private MessageTemplate mt; // The template to receive replies
        private int step = 0;

        public void action() {
            switch (step) {
                case 0:
                    // Send the cfp to all supermarkets
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    for (int i = 0; i < supermarketAgents.length; ++i) {
                        cfp.addReceiver(supermarketAgents[i]);
                    }
                    cfp.setContent(bill.getJson().toString());
                    cfp.setConversationId("supermarket-trade");
                    cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Unique value
                    myAgent.send(cfp);
                    // Prepare the template to get proposals from only supermakets that respond to the call for proposal
                    //search for supermarket trade and respond to cfp and proposal
                    mt = MessageTemplate.and(MessageTemplate.and(MessageTemplate.MatchConversationId("supermarket-trade"),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith())), MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
                    step = 1;
                    break;
                case 1:
                    // Receive all proposals/refusals from seller agents
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        // Get storage offered price for total shopping list
                        float price = Float.valueOf(reply.getContent());
                        if (bestSeller == null || price < bestPrice) {
                            // This is the best offer at present
                            bestPrice = price;
                            bestSeller = reply.getSender();
                        }
                        repliesCnt++;
                        if (repliesCnt >= supermarketAgents.length) {
                            // We received all replies
                            step = 2;
                        }
                    } else {
                        System.out.println("Got no message");
                        block();
                    }
                    break;
                case 2:
                    // Send the purchase order to the seller that provided the best offer
                    ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    System.out.println("sending offer to " + bestSeller.getLocalName());
                    order.addReceiver(bestSeller);
                    order.setContent(bill.getJson().toString());
                    order.setConversationId("supermarket-trade-accept");
                    order.setReplyWith("order" + System.currentTimeMillis());
                    myAgent.send(order);
                    // Prepare the template to get the purchase order reply
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("supermarket-trade-accept"),
                            MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                    step = 3;
                    break;

                case 3:
                    // Receive the purchase order reply
                    System.out.println("Waiting for reply");
                    reply = myAgent.receive(mt);
                    if (reply != null) {
                        System.out.println("I did get an inform reply &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        // Purchase order reply received
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            // Purchase successful. We can terminate
                            System.out.println(bill.getJson().toString() + " successfully purchased. VVVVVVVVVVVVVVVVVVVVVVVVVv");
                            System.out.println("Bill Price = " + bestPrice);
                            //add food to storage
                            foodStorage.addFood(bill);
                            bill.foods.clear();
                            //clear bill
                            bill = foodStorage.checkStorageRebuy();

                            storageInformRecipe(myAgent);

                        }
                        step = 4;
                    } else {
                        System.out.println("I didnt get an inform reply QQQQQQQQQQQQQQQQQQQQQQQQQ");
                        block();
                    }
                    break;
            }
        }

        public boolean done() {
            return step == 4;
        }
    } // End of inner class RequestPerformer

}
