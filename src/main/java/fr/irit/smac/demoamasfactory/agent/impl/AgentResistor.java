package fr.irit.smac.demoamasfactory.agent.impl;

import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.amasfactory.agent.impl.Agent;
import fr.irit.smac.amasfactory.message.EMessageType;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.amasfactory.message.Message;
import fr.irit.smac.demoamasfactory.agent.features.MyFeatures;
import fr.irit.smac.demoamasfactory.agent.features.dipole.KnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.SkillResistor;
import fr.irit.smac.demoamasfactory.agent.features.plot.KnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.SkillPlot;
import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge.Terminal;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public class AgentResistor<F extends MyFeatures, P extends Feature<K, S>, K extends KnowledgeResistor, S extends SkillResistor<K>>
    extends Agent<F, P, K, S>implements ITwoStepsAgent {

    public AgentResistor() {
    }

    @Override
    public void perceive() {

        this.commonFeatures.getFeatureSocial().getKnowledge().getMsgBox().getMsgs()
            .forEach(m -> this.commonFeatures.getFeatureResistor().getSkill().processMsg(m));
    }

    @Override
    public void decideAndAct() {
        // if (knowledge != null) {
        // monitor values
        IMsgBox<IMessage> msgBox = this.commonFeatures.getFeatureSocial().getKnowledge().getMsgBox();
        SkillPlot<KnowledgePlot> skillPlot = this.commonFeatures.getFeaturePlot().getSkill();

        String id = this.commonFeatures.getFeatureBasic().getKnowledge().getId();

        KnowledgeResistor knowledge = this.primaryFeature.getKnowledge();

        this.primaryFeature.getSkill().publishValues(skillPlot, id);

        // send message to terminals (in order to be added in their
        // neighborhood)

        if (knowledge.getV(Terminal.FIRST) == null) {
            Message msg = new Message(EMessageType.SIMPLE, id);
            msgBox.send(msg, knowledge.getId(Terminal.FIRST));
        }
        if (knowledge.getV(Terminal.SECOND) == null) {
            Message msg = new Message(EMessageType.SIMPLE, id);
            msgBox.send(msg, knowledge.getId(Terminal.SECOND));
        }

        this.primaryFeature.getSkill().communicateIntensity(msgBox, id);
        this.primaryFeature.getSkill().requetPotentialUpdate(msgBox, id);
    }
}
