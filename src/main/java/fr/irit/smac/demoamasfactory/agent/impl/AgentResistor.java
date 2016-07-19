package fr.irit.smac.demoamasfactory.agent.impl;

import org.slf4j.Logger;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.features.social.ISkillSocial;
import fr.irit.smac.amasfactory.agent.impl.TwoStepAgent;
import fr.irit.smac.demoamasfactory.agent.features.IMyCommonFeatures;
import fr.irit.smac.demoamasfactory.agent.features.dipole.IKnowledgeDipole.ETerminal;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.IKnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.ISkillResistor;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;

public class AgentResistor
    extends TwoStepAgent<IMyCommonFeatures, IKnowledgeResistor, ISkillResistor<IKnowledgeResistor>> {

    public AgentResistor() {
    }

    @Override
    public void setLogger(Logger logger) {
        super.setLogger(logger);
        commonFeatures.getFeaturePlot().getSkill().setLogger(logger);
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

        knowledgeSocial.getPortMap().get(ETerminal.FIRST.getName()).getValue().iterator()
            .forEachRemaining(o -> knowledge.setFirstPotential((Double) o));
        knowledgeSocial.getPortMap().get(ETerminal.SECOND.getName()).getValue().iterator()
            .forEachRemaining(o -> knowledge.setSecondPotential((Double) o));

        skill.publishValues(skillPlot, id);

        if (knowledge.getFirstPotential() == null
            || knowledge.getSecondPotential() == null) {
            skillSocial.sendPort(id);
        }

        skill.communicateIntensity(knowledgeSocial, skillSocial, id);
        skill.requestPotentialUpdate(knowledgeSocial, skillSocial, id);

        skillSocial.clearPortMap();
    }
}
