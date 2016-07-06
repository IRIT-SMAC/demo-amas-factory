package fr.irit.smac.demoamasfactory.agent.impl;

import org.slf4j.Logger;

import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.amasfactory.agent.features.social.impl.Target;
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

        this.primaryFeature.getSkill().publishValue(this.commonFeatures.getFeaturePlot().getSkill(),
            this.commonFeatures.getFeatureBasic().getKnowledge().getId());
    }

    @Override
    public void decideAndAct() {

        this.commonFeatures.getFeatureSocial().getKnowledge().getPortOfTargetMessageCollection().forEach(m -> {
            Target target = new Target(m.getSender(),
                m.getPortSource(), null);
            this.commonFeatures.getFeatureSocial().getKnowledge().getTargetSet().add(target);
        });

        IKnowledgeNode knowledgeNode = this.primaryFeature.getKnowledge();
        knowledgeNode.getPotentialDirectionRequest().forEach(m -> {
            knowledgeNode.setReceivedPdr(true);
            if (m.knownValue.equals(knowledgeNode.getPotential().getValue())
                && knowledgeNode.getWorstPotentialCriticality() < m.criticality) {
                // logger.debug("received " + pdr);
                knowledgeNode.setWorstPotentialCriticality(m.criticality);
                knowledgeNode.setPotentialDirection(m.direction);
            }
        });
        
        knowledgeNode.getIntensityMsg().forEach(m -> knowledgeNode.getIntensities().put(m.getSender(), m.getValue()));


        ISkillNode<K> skill = this.primaryFeature.getSkill();
        skill.applyKirchhoffLaw();
        skill.adjustPotential();

        IKnowledgeNode knowledge = this.primaryFeature
            .getKnowledge();
        this.commonFeatures.getFeatureSocial().getKnowledge().setOutputValue(knowledge.getPotential().getValue());
        this.commonFeatures.getFeatureSocial().getSkill().sendOutputValue(this.id);

        skill.cleanKnowledge();

    }

}
