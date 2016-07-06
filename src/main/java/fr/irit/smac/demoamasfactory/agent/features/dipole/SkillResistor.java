package fr.irit.smac.demoamasfactory.agent.features.dipole;

import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.agent.features.plot.KnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.SkillPlot;
import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge.Terminal;
import fr.irit.smac.demoamasfactory.message.impl.DirectionRequest;
import fr.irit.smac.demoamasfactory.message.impl.IntensityDirectionRequest;
import fr.irit.smac.demoamasfactory.message.impl.IntensityMsg;
import fr.irit.smac.demoamasfactory.message.impl.PotentialDirectionRequest;
import fr.irit.smac.libs.tooling.avt.EFeedback;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;

public class SkillResistor<K extends KnowledgeResistor> extends SkillDipole<K> {

    @Override
    public void processMsg(IMessage m) {
        super.processMsg(m);
        if (m instanceof IntensityDirectionRequest) {
            IntensityDirectionRequest idr = (IntensityDirectionRequest) m;
            EFeedback direction = idr.direction;
            Double knownValue = idr.knownValue;
            if (idr.getSender().equals(knowledge.getId(Terminal.SECOND))) {
                direction = DirectionRequest.opposite(direction);
                knownValue = -knownValue;
            }

            if (this.knowledge.getI().equals(knownValue)) {
                if (this.knowledge.getWorstIntensityCriticality() < idr.criticality) {
                    this.knowledge.setWorstIntensityCriticality(idr.criticality);
                    this.knowledge.setIntensityDirection(direction);
//                    logger.debug("received I" + idr.direction + " from " + idr.getSender());
                }
            }
        }
    }
    
    public void publishValues(SkillPlot<KnowledgePlot> skillPlot, String id) {
        
        if (knowledge.getR() != null) {
            skillPlot.publish("resistor", knowledge.getR(),id);
        }
        if (knowledge.getI() != null) {
            skillPlot.publish("intensity", knowledge.getI(),id);
        }
        if (knowledge.getU() != null) {
            skillPlot.publish("tension", knowledge.getU(),id);
        }
//        logger.debug(knowledge.toString());
    }
    
    public void communicateIntensity(IMsgBox<IMessage> msgBox, String id) {
        
        final Double intensity = knowledge.getI();
        if (intensity != null) {
            IntensityMsg firstMsg = new IntensityMsg(id, intensity);
            msgBox.send(firstMsg, knowledge.getId(Terminal.FIRST));
            IntensityMsg secondMsg = new IntensityMsg(id, -intensity);
            msgBox.send(secondMsg, knowledge.getId(Terminal.SECOND));
        }
    }
    
    public void requetPotentialUpdate(IMsgBox<IMessage> msgBox, String id) {
        
        EFeedback intensityDirection = knowledge.getIntensityDirection();
        Double worstIntensityCriticality = knowledge.getWorstIntensityCriticality();
        if (intensityDirection != null) {
            PotentialDirectionRequest firstMsg = new PotentialDirectionRequest(id,
                DirectionRequest.opposite(intensityDirection), worstIntensityCriticality,
                knowledge.getV(Terminal.FIRST));
            msgBox.send(firstMsg, knowledge.getId(Terminal.FIRST));
//            logger.debug("send V" + DirectionRequest.opposite(intensityDirection) + " to "
//                + knowledge.getId(Terminal.FIRST));
            PotentialDirectionRequest secondMsg = new PotentialDirectionRequest(id,
                intensityDirection, worstIntensityCriticality, knowledge.getV(Terminal.SECOND));
            msgBox.send(secondMsg, knowledge.getId(Terminal.SECOND));
//            logger.debug("send V" + intensityDirection + " to " + knowledge.getId(Terminal.SECOND));
            // clear request
            worstIntensityCriticality = 0d;
            intensityDirection = null;
        }
    }
    
}
