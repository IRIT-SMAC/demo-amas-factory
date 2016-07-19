package fr.irit.smac.demoamasfactory.agent.impl;

import org.slf4j.Logger;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.features.social.ISkillSocial;
import fr.irit.smac.amasfactory.agent.impl.TwoStepAgent;
import fr.irit.smac.demoamasfactory.agent.features.IMyCommonFeatures;
import fr.irit.smac.demoamasfactory.agent.features.node.IKnowledgeNode;
import fr.irit.smac.demoamasfactory.agent.features.node.ISkillNode;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;

public class AgentNode
    extends TwoStepAgent<IMyCommonFeatures, IKnowledgeNode, ISkillNode<IKnowledgeNode>> {

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

        String id = this.commonFeatures.getFeatureBasic().getKnowledge().getId();

        skill.handlePotentialDirection(knowledgeSocial);
        skill.handleIntensityMessage(knowledgeSocial);
        skill.publishValue(skillPlot,
            id);
        skill.applyKirchhoffLaw();
        skill.adjustPotential();

        knowledgeSocial.setOutputValue(knowledge.getPotential().getValue());
        skillSocial.sendOutputValue(id);
        skill.cleanKnowledge();

        skillSocial.clearPortMap();
    }

}
