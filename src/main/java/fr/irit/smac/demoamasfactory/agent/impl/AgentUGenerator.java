package fr.irit.smac.demoamasfactory.agent.impl;

import fr.irit.smac.amasfactory.agent.impl.Agent;
import fr.irit.smac.amasfactory.message.EMessageType;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.amasfactory.message.Message;
import fr.irit.smac.demoamasfactory.agent.features.MyFeatures;
import fr.irit.smac.demoamasfactory.agent.features.dipole.KnowledgeUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.plot.KnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.SkillPlot;
import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge.Terminal;
import fr.irit.smac.demoamasfactory.message.impl.PotentialDirectionRequest;
import fr.irit.smac.libs.tooling.avt.EFeedback;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public class AgentUGenerator<F extends MyFeatures> extends Agent<F>implements ITwoStepsAgent{

    public AgentUGenerator() {
    }

    @Override
    public void perceive() {

        this.features.getFeatureSocial().getKnowledge().getMsgBox().getMsgs()
            .forEach(m -> this.features.getFeatureUGenerator().getSkill().processMsg(m));
    }
    
    @Override
    public void decideAndAct() {
        // monitor agent
        KnowledgeUGenerator knowledgeGenerator = this.features.getFeatureUGenerator().getKnowledge();
        IMsgBox<IMessage> msgBox = this.features.getFeatureSocial().getKnowledge().getMsgBox();

        SkillPlot<KnowledgePlot> skillPlot = this.features.getFeaturePlot().getSkill();
        
        String id = this.features.getFeatureBasic().getKnowledge().getId();
        skillPlot.publish("tension", knowledgeGenerator.getU(),id);
        if (knowledgeGenerator.getV(Terminal.FIRST) != null) {
            skillPlot.publish("firstPotential",
                knowledgeGenerator.getV(Terminal.FIRST),id);
        }
        if (knowledgeGenerator.getV(Terminal.SECOND) != null) {
            skillPlot.publish("secondPotential",
                knowledgeGenerator.getV(Terminal.SECOND),id);
        }
        logger.debug(knowledgeGenerator.toString());

        if (knowledgeGenerator.getV(Terminal.FIRST) == null
            || knowledgeGenerator.getV(Terminal.SECOND) == null) {
            // send message to terminals (in order to be added in their
            // neighborhood)
            Message msg = new Message(EMessageType.SIMPLE, id);
            msgBox.send(msg, knowledgeGenerator.getId(Terminal.FIRST));
            msgBox.send(msg, knowledgeGenerator.getId(Terminal.SECOND));
        }
        else {
            // compare actual voltage with generator tension
            Double actualVoltage = knowledgeGenerator.getV(Terminal.SECOND)
                - knowledgeGenerator.getV(Terminal.FIRST);
            Double error = knowledgeGenerator.getU() - actualVoltage;
            if (error > 0) {
                // ask to increase the actualVoltage
                requestUChange(Terminal.FIRST, Terminal.SECOND);
            }
            else if (error < 0) {
                // ask to decrease the actualVoltage
                requestUChange(Terminal.SECOND, Terminal.FIRST);
            }
        }
    }

    private void requestUChange(Terminal lowerReceiver, Terminal greaterReceiver) {
        
        KnowledgeUGenerator knowledgeGenerator = this.features.getFeatureUGenerator().getKnowledge();
        IMsgBox<IMessage> msgBox = this.features.getFeatureSocial().getKnowledge().getMsgBox();

        Message greaterMsg = new PotentialDirectionRequest(id,
            EFeedback.GREATER, 100d, knowledgeGenerator.getV(greaterReceiver));
        Message lowerMsg = new PotentialDirectionRequest(id,
            EFeedback.LOWER, 100d, knowledgeGenerator.getV(lowerReceiver));
        msgBox.send(greaterMsg,
            knowledgeGenerator.getId(greaterReceiver));
        msgBox.send(lowerMsg, knowledgeGenerator.getId(lowerReceiver));
        logger.debug(
            "Sent msg: UP: " + knowledgeGenerator.getId(greaterReceiver)
                + "  DOWN: " + knowledgeGenerator.getId(lowerReceiver));
    }
}
