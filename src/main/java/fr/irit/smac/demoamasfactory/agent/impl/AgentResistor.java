package fr.irit.smac.demoamasfactory.agent.impl;

import org.slf4j.Logger;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.impl.TwoStepAgent;
import fr.irit.smac.amasfactory.message.ValuePortMessage;
import fr.irit.smac.demoamasfactory.agent.features.IMyCommonFeatures;
import fr.irit.smac.demoamasfactory.agent.features.dipole.IKnowledgeDipole.ETerminal;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.IKnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.ISkillResistor;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;

public class AgentResistor<F extends IMyCommonFeatures, K extends IKnowledgeResistor, S extends ISkillResistor<K>>
    extends TwoStepAgent<F, K, S> {

    public AgentResistor() {
    }

    @Override
    public void setLogger(Logger logger) {
        super.setLogger(logger);
        this.commonFeatures.getFeaturePlot().getSkill().setLogger(logger);
    }

    @Override
    public void perceive() {

        IKnowledgeSocial knowledgeSocial = this.commonFeatures.getFeatureSocial().getKnowledge();
        knowledgeSocial.getMsgBox().getMsgs()
            .forEach(m -> this.getSkill().processMsg(m, knowledgeSocial));
    }

    @Override
    public void decideAndAct() {

        IKnowledgeResistor knowledgeResistor = this.getKnowledge();

        // set first and second potential
        this.commonFeatures.getFeatureSocial().getKnowledge().getSendToTargetMessageCollection().forEach(m -> {
            ValuePortMessage message = (ValuePortMessage) m;
            if (message.getPort().equals(ETerminal.FIRST.getName())) {
                knowledgeResistor.setFirstPotential((Double) message.getValue());
            }
            else if (message.getPort().equals(ETerminal.SECOND.getName())) {
                knowledgeResistor.setSecondPotential((Double) message.getValue());
            }
        });

        this.commonFeatures.getFeatureSocial().getKnowledge().getSendPortToTargetMessageCollection().clear();
        this.commonFeatures.getFeatureSocial().getKnowledge().getSendToTargetMessageCollection().clear();

        String id = this.commonFeatures.getFeatureBasic().getKnowledge().getId();

        ISkillPlot<IKnowledgePlot> skillPlot = this.commonFeatures.getFeaturePlot().getSkill();
        this.getSkill().publishValues(skillPlot, id);

        // send message to terminals (in order to be added in their
        // neighborhood)
        if (this.getKnowledge().getFirstPotential() == null
            || this.getKnowledge().getSecondPotential() == null) {
            this.commonFeatures.getFeatureSocial().getSkill()
                .sendPort(this.commonFeatures.getFeatureBasic().getKnowledge().getId());
        }

        IKnowledgeSocial knowledgeSocial = this.commonFeatures.getFeatureSocial()
            .getKnowledge();
        this.getSkill().communicateIntensity(knowledgeSocial,
            this.commonFeatures.getFeatureSocial().getSkill(), id);
        this.getSkill().requestPotentialUpdate(knowledgeSocial,
            this.commonFeatures.getFeatureSocial().getSkill(), id);
    }
}
