package fr.irit.smac.demoamasfactory.agent.impl;

import org.slf4j.Logger;

import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.amasfactory.agent.impl.Agent;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.agent.features.MyFeatures;
import fr.irit.smac.demoamasfactory.agent.features.node.IKnowledgeNode;
import fr.irit.smac.demoamasfactory.agent.features.node.ISkillNode;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public class AgentNode<F extends MyFeatures, K extends IKnowledgeNode, S extends ISkillNode<K>, P extends Feature<K, S>>
    extends Agent<F, K, S, P>
    implements ITwoStepsAgent {

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
            m -> this.primaryFeature.getSkill().processMsg(m, this.commonFeatures.getFeatureSocial().getKnowledge()));
    }

    @Override
    public void decideAndAct() {

        this.commonFeatures.getFeatureSocial().getSkill().addTargetFromMessage();
        ISkillNode<K> skill = this.primaryFeature.getSkill();
        skill.handlePotentialDirectionRequestMessage();
        skill.handleIntensityMessage();
        skill.publishValue(this.commonFeatures.getFeaturePlot().getSkill(),
            this.commonFeatures.getFeatureBasic().getKnowledge().getId());
        
        this.commonFeatures.getFeatureSocial().getKnowledge().getPortOfTargetMessageCollection().clear();
        this.commonFeatures.getFeatureSocial().getKnowledge().getValuePortMessageCollection().clear();
        this.primaryFeature.getKnowledge().getIntensityMsg().clear();
        this.primaryFeature.getKnowledge().getPotentialDirectionRequest().clear();
        skill.applyKirchhoffLaw();
        skill.adjustPotential();

        IKnowledgeNode knowledge = this.primaryFeature.getKnowledge();
        this.commonFeatures.getFeatureSocial().getKnowledge().setOutputValue(knowledge.getPotential().getValue());
        this.commonFeatures.getFeatureSocial().getSkill().sendOutputValue(this.id);
        skill.cleanKnowledge();
        
        

    }

}
