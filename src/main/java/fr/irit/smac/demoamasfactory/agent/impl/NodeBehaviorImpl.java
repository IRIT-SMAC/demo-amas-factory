package fr.irit.smac.demoamasfactory.agent.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.demoamasfactory.knowledge.impl.NodeKnowledgeImpl;
import fr.irit.smac.demoamasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.message.impl.IntensityMsg;
import fr.irit.smac.demoamasfactory.message.impl.PotentialDirectionRequest;
import fr.irit.smac.demoamasfactory.message.impl.PotentialMsg;
import fr.irit.smac.libs.tooling.avt.EFeedback;

public class NodeBehaviorImpl extends AbstractAgent {

    @JsonProperty
    NodeKnowledgeImpl knowledge;
    private Double    previousISumChange = 0d;
    private boolean   receivedPdr;

    public NodeBehaviorImpl() {

    }

    @Override
    public void perceive() {
        msgBox.getMsgs().forEach(m -> processMsg(m));
        monitor.publish("potential",
            knowledge.potential.getValue());
        logger.debug("potential: " +
            knowledge.potential.getValue());
        if (knowledge.intensities.get("R1") != null) {
            monitor.publish("R1", knowledge.intensities.get("R1"));
        }
        if (knowledge.intensities.get("R2") != null) {
            monitor.publish("R2", knowledge.intensities.get("R2"));
        }
        if (knowledge.intensities.get("R3") != null) {
            monitor.publish("R3", knowledge.intensities.get("R3"));
        }

    }

    private void processMsg(IMessage m) {
        knowledge.neighbors.add(m.getSender());
        if (m instanceof PotentialDirectionRequest) {
            PotentialDirectionRequest pdr = (PotentialDirectionRequest) m;
            receivedPdr = true;
            if (pdr.knownValue.equals(knowledge.potential.getValue())
                && knowledge.worstPotentialCriticality < pdr.criticality) {
                logger.debug("received " + pdr);
                knowledge.worstPotentialCriticality = pdr.criticality;
                knowledge.potentialDirection = pdr.direction;
            }
        }
        else if (m instanceof IntensityMsg) {
            IntensityMsg im = (IntensityMsg) m;
            knowledge.intensities.put(im.getSender(), im.getValue());
        }
    }

    @Override
    public void decideAndAct() {
        // kirchhoff law
        Double intensitiesSum = 0d;
        for (Double intensity : knowledge.intensities.values()) {
            intensitiesSum += intensity;
        }
        if (!receivedPdr && intensitiesSum != 0) {
            if (knowledge.potentialDirection == null && !previousISumChange.equals(intensitiesSum)) {
                logger.debug("Isum: " + intensitiesSum + " previousChange: " + previousISumChange);
                previousISumChange = intensitiesSum;
                if (intensitiesSum > 0) {
                    knowledge.potential.adjustValue(EFeedback.GREATER);
                }
                else if (intensitiesSum < 0) {
                    knowledge.potential.adjustValue(EFeedback.LOWER);
                }
            }
        }

        // potential tuning
        if (knowledge.potentialDirection != null) {
            logger.debug(
                "potential=" + knowledge.potential.getValue() + " adjust it " +
                    knowledge.potentialDirection);
            knowledge.potential.adjustValue(knowledge.potentialDirection);
        }
        sendPotential();

        // knowledge cleaning
        knowledge.potentialDirection = null;
        knowledge.worstPotentialCriticality = 0d;
        receivedPdr = false;
    }

    private void sendPotential() {
        logger.debug("send msg: potential=" +
            knowledge.potential.getValue());
        PotentialMsg message = new PotentialMsg(knowledge.getId(), knowledge.potential.getValue());
        for (String receiver : knowledge.neighbors) {
            msgBox.send(message, receiver);
        }
    }
}
