package fr.irit.smac.demoamasfactory.agent.impl;

import org.slf4j.Logger;

import fr.irit.smac.amasfactory.agent.impl.TwoStepAgent;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.agent.features.IMyCommonFeatures;
import fr.irit.smac.demoamasfactory.agent.features.node.IKnowledgeNode;
import fr.irit.smac.demoamasfactory.agent.features.node.ISkillNode;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;

public class AgentNode<F extends IMyCommonFeatures, K extends IKnowledgeNode, S extends ISkillNode<K>>
    extends TwoStepAgent<F, K, S> {

    public AgentNode() {

    }

    @Override
    public void setLogger(Logger logger) {
        super.setLogger(logger);
        this.commonFeatures.getFeaturePlot().getSkill().setLogger(logger);
    }

    @Override
    public void perceive() {

        IMsgBox<IMessage> msgBox = this.commonFeatures.getFeatureSocial().getKnowledge().getMsgBox();
        msgBox.getMsgs().forEach(
            m -> this.getSkill().processMsg(m, this.commonFeatures.getFeatureSocial().getKnowledge()));
    }

    @Override
    public void decideAndAct() {

        this.commonFeatures.getFeatureSocial().getSkill().addTargetFromMessage();

        String id = this.commonFeatures.getFeatureBasic().getKnowledge().getId();
        
        ISkillNode<K> skill = this.getSkill();
        skill.handlePotentialDirectionRequestMessage(this.commonFeatures.getFeatureSocial().getKnowledge());
        skill.handleIntensityMessage(this.commonFeatures.getFeatureSocial().getKnowledge());
        skill.publishValue(this.commonFeatures.getFeaturePlot().getSkill(),
            id);

        this.commonFeatures.getFeatureSocial().getKnowledge().getSendPortToTargetMessageCollection().clear();
        this.commonFeatures.getFeatureSocial().getKnowledge().getSendToTargetMessageCollection().clear();
        skill.applyKirchhoffLaw();
        skill.adjustPotential();

        IKnowledgeNode knowledge = this.getKnowledge();
        this.commonFeatures.getFeatureSocial().getKnowledge().setOutputValue(knowledge.getPotential().getValue());
        this.commonFeatures.getFeatureSocial().getSkill().sendOutputValue(id);
        skill.cleanKnowledge();

    }

}
