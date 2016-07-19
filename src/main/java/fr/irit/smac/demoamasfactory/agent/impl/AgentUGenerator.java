package fr.irit.smac.demoamasfactory.agent.impl;

import org.slf4j.Logger;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.features.social.ISkillSocial;
import fr.irit.smac.amasfactory.agent.features.social.ITarget;
import fr.irit.smac.amasfactory.agent.impl.TwoStepAgent;
import fr.irit.smac.amasfactory.message.PortOfTargetMessage;
import fr.irit.smac.demoamasfactory.agent.features.IMyCommonFeatures;
import fr.irit.smac.demoamasfactory.agent.features.dipole.IKnowledgeDipole.ETerminal;
import fr.irit.smac.demoamasfactory.agent.features.dipole.generator.IKnowledgeUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.dipole.generator.ISkillUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;

public class AgentUGenerator
    extends TwoStepAgent<IMyCommonFeatures, IKnowledgeUGenerator, ISkillUGenerator<IKnowledgeUGenerator>> {

    public AgentUGenerator() {
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
            ITarget target = knowledgeSocial.getTargetMap().get("firstTerminal");
            String agentId = target.getAgentId();
            String portTarget = target.getPortTarget();
            String portSource = target.getPortSource();

            knowledgeSocial.getMsgBox().send(new PortOfTargetMessage(portTarget, portSource, id, id),
                agentId);

            ITarget target2 = knowledgeSocial.getTargetMap().get("secondTerminal");
            String agentId2 = target2.getAgentId();
            String portTarget2 = target2.getPortTarget();
            String portSource2 = target2.getPortSource();

            knowledgeSocial.getMsgBox().send(new PortOfTargetMessage(portTarget2, portSource2, id, id),
                agentId2);
        }
        else {
            skill.compareVoltageWithGeneratorTension(knowledgeSocial, skillSocial, id);
        }

        skillSocial.clearPortMap();

    }
}
