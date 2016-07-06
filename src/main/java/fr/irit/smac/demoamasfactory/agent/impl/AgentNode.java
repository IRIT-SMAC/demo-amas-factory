package fr.irit.smac.demoamasfactory.agent.impl;

import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.amasfactory.agent.features.social.impl.Target;
import fr.irit.smac.amasfactory.agent.impl.Agent;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.agent.features.MyFeatures;
import fr.irit.smac.demoamasfactory.agent.features.node.KnowledgeNode;
import fr.irit.smac.demoamasfactory.agent.features.node.SkillNode;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public class AgentNode<F extends MyFeatures, K extends KnowledgeNode, S extends SkillNode<K>, P extends Feature<K, S>>
    extends Agent<F, K, S, P>
    implements ITwoStepsAgent {

    public AgentNode() {

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

        KnowledgeNode knowledgeNode = this.primaryFeature.getKnowledge();
        knowledgeNode.getPotentialDirectionRequest().forEach(m -> {
            knowledgeNode.setReceivedPdr(true);
            if (m.knownValue.equals(knowledgeNode.potential.getValue())
                && knowledgeNode.worstPotentialCriticality < m.criticality) {
                // logger.debug("received " + pdr);
                knowledgeNode.worstPotentialCriticality = m.criticality;
                knowledgeNode.potentialDirection = m.direction;
            }
        });
        
        knowledgeNode.getIntensityMsg().forEach(m -> knowledgeNode.intensities.put(m.getSender(), m.getValue()));


        SkillNode<K> skill = this.primaryFeature.getSkill();
        skill.applyKirchhoffLaw();
        skill.adjustPotential();

        KnowledgeNode knowledge = this.primaryFeature
            .getKnowledge();
        this.commonFeatures.getFeatureSocial().getKnowledge().setOutputValue(knowledge.potential.getValue());
        this.commonFeatures.getFeatureSocial().getSkill().sendOutputValue(this.id);

        skill.cleanKnowledge();

    }

}
