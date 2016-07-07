package fr.irit.smac.demoamasfactory.agent.impl;

import org.slf4j.Logger;

import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.impl.Agent;
import fr.irit.smac.amasfactory.message.ValuePortMessage;
import fr.irit.smac.demoamasfactory.agent.features.MyFeatures;
import fr.irit.smac.demoamasfactory.agent.features.dipole.IKnowledgeDipole.Terminal;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.IKnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.ISkillResistor;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public class AgentResistor<F extends MyFeatures, K extends IKnowledgeResistor, S extends ISkillResistor<K>, P extends Feature<K, S>>
    extends Agent<F, K, S, P>implements ITwoStepsAgent {

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
            .forEach(m -> this.primaryFeature.getSkill().processMsg(m, knowledgeSocial));
    
        IKnowledgeResistor knowledgeResistor = this.primaryFeature.getKnowledge();

        // set first and second potential
        this.commonFeatures.getFeatureSocial().getKnowledge().getValuePortMessageCollection().forEach(m -> {
            ValuePortMessage message = (ValuePortMessage) m;
            if (message.getPort().equals(Terminal.FIRST.getName())) {
                knowledgeResistor.setFirstPotential((Double) message.getValue());
            }
            else if (message.getPort().equals(Terminal.SECOND.getName())) {
                knowledgeResistor.setSecondPotential((Double) message.getValue());
            }
        });

        this.primaryFeature.getSkill().handleIntensityDirectionRequest();
        
        this.commonFeatures.getFeatureSocial().getKnowledge().getPortOfTargetMessageCollection().clear();
        this.commonFeatures.getFeatureSocial().getKnowledge().getValuePortMessageCollection().clear();
        this.primaryFeature.getKnowledge().getIntensityDirectionRequest().clear();
    }

    @Override
    public void decideAndAct() {

        String id = this.commonFeatures.getFeatureBasic().getKnowledge().getId();

        ISkillPlot<IKnowledgePlot> skillPlot = this.commonFeatures.getFeaturePlot().getSkill();
        this.primaryFeature.getSkill().publishValues(skillPlot, id);


        // send message to terminals (in order to be added in their
        // neighborhood)
        if (this.primaryFeature.getKnowledge().getFirstPotential() == null
            || this.primaryFeature.getKnowledge().getSecondPotential() == null) {
            this.commonFeatures.getFeatureSocial().getSkill()
                .sendPort(this.commonFeatures.getFeatureBasic().getKnowledge().getId());
        }

        IKnowledgeSocial knowledgeSocial = this.commonFeatures.getFeatureSocial()
            .getKnowledge();
        this.primaryFeature.getSkill().communicateIntensity(knowledgeSocial, id);
        this.primaryFeature.getSkill().requestPotentialUpdate(knowledgeSocial, id);
    }
}
