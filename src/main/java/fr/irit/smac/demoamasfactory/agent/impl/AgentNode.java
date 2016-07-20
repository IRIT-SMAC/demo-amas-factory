package fr.irit.smac.demoamasfactory.agent.impl;

import org.slf4j.Logger;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.features.social.ISkillSocial;
import fr.irit.smac.amasfactory.agent.impl.Agent;
import fr.irit.smac.demoamasfactory.agent.features.IMyCommonFeatures;
import fr.irit.smac.demoamasfactory.agent.features.node.IKnowledgeNode;
import fr.irit.smac.demoamasfactory.agent.features.node.ISkillNode;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public class AgentNode
    extends Agent<IMyCommonFeatures, IKnowledgeNode, ISkillNode<IKnowledgeNode>> implements ITwoStepsAgent {

    public AgentNode() {

    }

    @Override
    public void setLogger(Logger logger) {
        super.setLogger(logger);
        this.commonFeatures.getFeaturePlot().getSkill().setLogger(logger);
    }

    @Override
    public void perceive() {

        ISkillSocial<IKnowledgeSocial> skillSocial = commonFeatures.getFeatureSocial().getSkill();
        IKnowledgeSocial knowledgeSocial = commonFeatures.getFeatureSocial().getKnowledge();

        knowledgeSocial.getMsgBox().getMsgs()
            .forEach(m -> skill.processMsg(skillSocial, m));
    }

    @Override
    public void decideAndAct() {

        ISkillSocial<IKnowledgeSocial> skillSocial = commonFeatures.getFeatureSocial().getSkill();
        IKnowledgeSocial knowledgeSocial = commonFeatures.getFeatureSocial().getKnowledge();
        ISkillPlot<IKnowledgePlot> skillPlot = commonFeatures.getFeaturePlot().getSkill();

        String id = commonFeatures.getFeatureBasic().getKnowledge().getId();

        skill.handlePotentialDirection(knowledgeSocial);
        skill.handleIntensityMessage(knowledgeSocial);
        skill.publishValue(skillPlot,
            id);
        skill.applyKirchhoffLaw();
        skill.adjustPotential();

        knowledgeSocial.getTargetMap()
            .forEach((k, v) -> skillSocial.sendDataToPortTarget(v.getAgentId().concat(v.getPortTarget()), knowledge.getPotential().getValue(), id));
        skill.cleanKnowledge();

        skillSocial.clearPortMap();
    }

}
