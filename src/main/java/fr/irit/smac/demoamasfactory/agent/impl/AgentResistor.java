package fr.irit.smac.demoamasfactory.agent.impl;

import fr.irit.smac.amasfactory.agent.impl.Agent;
import fr.irit.smac.amasfactory.message.EMessageType;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.amasfactory.message.Message;
import fr.irit.smac.demoamasfactory.agent.features.MyFeatures;
import fr.irit.smac.demoamasfactory.agent.features.dipole.KnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.plot.KnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.SkillPlot;
import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge.Terminal;
import fr.irit.smac.demoamasfactory.message.impl.DirectionRequest;
import fr.irit.smac.demoamasfactory.message.impl.IntensityMsg;
import fr.irit.smac.demoamasfactory.message.impl.PotentialDirectionRequest;
import fr.irit.smac.libs.tooling.avt.EFeedback;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public class AgentResistor<F extends MyFeatures> extends Agent<F>implements ITwoStepsAgent{

    public AgentResistor() {
    }

    @Override
    public void perceive() {

        this.features.getFeatureSocial().getKnowledge().getMsgBox().getMsgs()
            .forEach(m -> this.features.getFeatureResistor().getSkill().processMsg(m));
    }
    
    @Override
    public void decideAndAct() {
        // if (knowledge != null) {
        // monitor values
        KnowledgeResistor knowledgeResistor = this.features.getFeatureResistor().getKnowledge();
        IMsgBox<IMessage> msgBox = this.features.getFeatureSocial().getKnowledge().getMsgBox();
        SkillPlot<KnowledgePlot> skillPlot = this.features.getFeaturePlot().getSkill();

        String id = this.features.getFeatureBasic().getKnowledge().getId();

        if (knowledgeResistor.getR() != null) {
            skillPlot.publish("resistor", knowledgeResistor.getR(),id);
        }
        if (knowledgeResistor.getI() != null) {
            skillPlot.publish("intensity", knowledgeResistor.getI(),id);
        }
        if (knowledgeResistor.getU() != null) {
            skillPlot.publish("tension", knowledgeResistor.getU(),id);
        }
        logger.debug(knowledgeResistor.toString());

        // send message to terminals (in order to be added in their
        // neighborhood)
        
        if (knowledgeResistor.getV(Terminal.FIRST) == null) {
            Message msg = new Message(EMessageType.SIMPLE, id);
            msgBox.send(msg, knowledgeResistor.getId(Terminal.FIRST));
        }
        if (knowledgeResistor.getV(Terminal.SECOND) == null) {
            Message msg = new Message(EMessageType.SIMPLE, id);
            msgBox.send(msg, knowledgeResistor.getId(Terminal.SECOND));
        }
        // communicate the intensity
        final Double intensity = knowledgeResistor.getI();
        if (intensity != null) {
            IntensityMsg firstMsg = new IntensityMsg(id, intensity);
            msgBox.send(firstMsg, knowledgeResistor.getId(Terminal.FIRST));
            IntensityMsg secondMsg = new IntensityMsg(id, -intensity);
            msgBox.send(secondMsg, knowledgeResistor.getId(Terminal.SECOND));
        }

        // request potential updates
        EFeedback intensityDirection = knowledgeResistor.getIntensityDirection();
        Double worstIntensityCriticality = knowledgeResistor.getWorstIntensityCriticality();
        if (intensityDirection != null) {
            PotentialDirectionRequest firstMsg = new PotentialDirectionRequest(id,
                DirectionRequest.opposite(intensityDirection), worstIntensityCriticality,
                knowledgeResistor.getV(Terminal.FIRST));
            msgBox.send(firstMsg, knowledgeResistor.getId(Terminal.FIRST));
            logger.debug("send V" + DirectionRequest.opposite(intensityDirection) + " to "
                + knowledgeResistor.getId(Terminal.FIRST));
            PotentialDirectionRequest secondMsg = new PotentialDirectionRequest(id,
                intensityDirection, worstIntensityCriticality, knowledgeResistor.getV(Terminal.SECOND));
            msgBox.send(secondMsg, knowledgeResistor.getId(Terminal.SECOND));
            logger.debug("send V" + intensityDirection + " to " + knowledgeResistor.getId(Terminal.SECOND));
            // clear request
            worstIntensityCriticality = 0d;
            intensityDirection = null;
        }
        // }
    }
}
