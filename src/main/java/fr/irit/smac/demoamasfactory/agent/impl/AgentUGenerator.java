package fr.irit.smac.demoamasfactory.agent.impl;

import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.amasfactory.agent.impl.Agent;
import fr.irit.smac.amasfactory.message.EMessageType;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.amasfactory.message.Message;
import fr.irit.smac.demoamasfactory.agent.features.MyFeatures;
import fr.irit.smac.demoamasfactory.agent.features.dipole.KnowledgeUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.dipole.SkillUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.plot.KnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.SkillPlot;
import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge.Terminal;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public class AgentUGenerator<F extends MyFeatures, P extends Feature<K, S>, K extends KnowledgeUGenerator, S extends SkillUGenerator<K>>
    extends Agent<F, P, K, S>implements ITwoStepsAgent {

    public AgentUGenerator() {
    }

    @Override
    public void perceive() {

        this.commonFeatures.getFeatureSocial().getKnowledge().getMsgBox().getMsgs()
            .forEach(m -> this.primaryFeature.getSkill().processMsg(m));
    }

    @Override
    public void decideAndAct() {
        // monitor agent
        IMsgBox<IMessage> msgBox = this.commonFeatures.getFeatureSocial().getKnowledge().getMsgBox();
        SkillPlot<KnowledgePlot> skillPlot = this.commonFeatures.getFeaturePlot().getSkill();

        KnowledgeUGenerator knowledge = this.primaryFeature.getKnowledge();

        String id = this.commonFeatures.getFeatureBasic().getKnowledge().getId();

        this.primaryFeature.getSkill().publishValues(skillPlot, id);
//        logger.debug(knowledge.toString());

        if (knowledge.getV(Terminal.FIRST) == null
            || knowledge.getV(Terminal.SECOND) == null) {
            // send message to terminals (in order to be added in their
            // neighborhood)
            Message msg = new Message(EMessageType.SIMPLE, id);
            msgBox.send(msg, knowledge.getId(Terminal.FIRST));
            msgBox.send(msg, knowledge.getId(Terminal.SECOND));
        }
        else {
            this.primaryFeature.getSkill().compareVoltageWithGeneratorTension(msgBox, id);
        }
    }
}
