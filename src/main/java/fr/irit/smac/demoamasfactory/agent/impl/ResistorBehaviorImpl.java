package fr.irit.smac.demoamasfactory.agent.impl;

import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge.Terminal;
import fr.irit.smac.demoamasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.message.impl.DirectionRequest;
import fr.irit.smac.demoamasfactory.message.impl.IntensityDirectionRequest;
import fr.irit.smac.demoamasfactory.message.impl.IntensityMsg;
import fr.irit.smac.demoamasfactory.message.impl.Message;
import fr.irit.smac.demoamasfactory.message.impl.PotentialDirectionRequest;
import fr.irit.smac.libs.tooling.avt.EFeedback;

public class ResistorBehaviorImpl extends AbstractDipoleBehavior {

    private Double    worstIntensityCriticality = 0d;
    private EFeedback intensityDirection;

    public ResistorBehaviorImpl() {
    }

    @Override
    protected void processMsg(IMessage m) {
        super.processMsg(m);
        if (m instanceof IntensityDirectionRequest) {
            IntensityDirectionRequest idr = (IntensityDirectionRequest) m;
            EFeedback direction = idr.direction;
            Double knownValue = idr.knownValue;
            if (idr.getSender().equals(knowledge.getId(Terminal.SECOND))) {
                direction = DirectionRequest.opposite(direction);
                knownValue = -knownValue;
            }

            if (knowledge.getI().equals(knownValue)) {
                if (worstIntensityCriticality < idr.criticality) {
                    worstIntensityCriticality = idr.criticality;
                    intensityDirection = direction;
                    logger.debug("received I" + idr.direction + " from " + idr.getSender());
                }
            }
        }
    }

    @Override
    public void decideAndAct() {
        if (knowledge != null) {
            // monitor values
            if (knowledge.getR() != null) {
                monitor.publish("resistor", knowledge.getR());
            }
            if (knowledge.getI() != null) {
                monitor.publish("intensity", knowledge.getI());
            }
            if (knowledge.getU() != null) {
                monitor.publish("tension", knowledge.getU());
            }
            logger.debug(knowledge.toString());

            // send message to terminals (in order to be added in their
            // neighborhood)
            if (knowledge.getV(Terminal.FIRST) == null) {
                Message msg = new Message(knowledge.getId());
                msgBox.send(msg, knowledge.getId(Terminal.FIRST));
            }
            if (knowledge.getV(Terminal.SECOND) == null) {
                Message msg = new Message(knowledge.getId());
                msgBox.send(msg, knowledge.getId(Terminal.SECOND));
            }
            // communicate the intensity
            final Double intensity = knowledge.getI();
            if (intensity != null) {
                IntensityMsg firstMsg = new IntensityMsg(knowledge.getId(), intensity);
                msgBox.send(firstMsg, knowledge.getId(Terminal.FIRST));
                IntensityMsg secondMsg = new IntensityMsg(knowledge.getId(), -intensity);
                msgBox.send(secondMsg, knowledge.getId(Terminal.SECOND));
            }

            // request potential updates
            if (intensityDirection != null) {
                PotentialDirectionRequest firstMsg = new PotentialDirectionRequest(knowledge.getId(),
                    DirectionRequest.opposite(intensityDirection), worstIntensityCriticality,
                    knowledge.getV(Terminal.FIRST));
                msgBox.send(firstMsg, knowledge.getId(Terminal.FIRST));
                logger.debug("send V" + DirectionRequest.opposite(intensityDirection) + " to "
                    + knowledge.getId(Terminal.FIRST));
                PotentialDirectionRequest secondMsg = new PotentialDirectionRequest(knowledge.getId(),
                    intensityDirection, worstIntensityCriticality, knowledge.getV(Terminal.SECOND));
                msgBox.send(secondMsg, knowledge.getId(Terminal.SECOND));
                logger.debug("send V" + intensityDirection + " to " + knowledge.getId(Terminal.SECOND));
                // clear request
                worstIntensityCriticality = 0d;
                intensityDirection = null;
            }
        }
    }

    @Override
    public void perceive() {
        // TODO Auto-generated method stub

    }
}
