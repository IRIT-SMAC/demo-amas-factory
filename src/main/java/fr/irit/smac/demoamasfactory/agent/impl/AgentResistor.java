package fr.irit.smac.demoamasfactory.agent.impl;

import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.amasfactory.agent.features.social.impl.KnowledgeSocial;
import fr.irit.smac.amasfactory.agent.impl.Agent;
import fr.irit.smac.amasfactory.message.ValuePortMessage;
import fr.irit.smac.demoamasfactory.agent.features.MyFeatures;
import fr.irit.smac.demoamasfactory.agent.features.dipole.KnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.SkillResistor;
import fr.irit.smac.demoamasfactory.agent.features.plot.KnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.SkillPlot;
import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge.Terminal;
import fr.irit.smac.demoamasfactory.message.impl.DirectionRequest;
import fr.irit.smac.libs.tooling.avt.EFeedback;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public class AgentResistor<F extends MyFeatures, K extends KnowledgeResistor, S extends SkillResistor<K>, P extends Feature<K, S>>
    extends Agent<F, K, S, P>implements ITwoStepsAgent {

    public AgentResistor() {
    }

    @Override
    public void perceive() {

        KnowledgeSocial knowledgeSocial = this.commonFeatures.getFeatureSocial().getKnowledge();
        knowledgeSocial.getMsgBox().getMsgs()
            .forEach(m -> this.primaryFeature.getSkill().processMsg(m, knowledgeSocial));
    }

    @Override
    public void decideAndAct() {
        
        KnowledgeResistor knowledgeResistor = this.primaryFeature.getKnowledge();
        
        this.commonFeatures.getFeatureSocial().getKnowledge().getValuePortMessageCollection().forEach(m -> {
            ValuePortMessage message = (ValuePortMessage) m;
            if (message.getPort().equals(Terminal.FIRST.getName())) {
                knowledgeResistor.setFirstPotential((Double) message.getValue());
            }
            else if (message.getPort().equals(Terminal.SECOND.getName())) {
                knowledgeResistor.setSecondPotential((Double) message.getValue());
            }
        });

        knowledgeResistor.getIntensityDirectionRequest().forEach(m -> {
            EFeedback direction = m.direction;
            Double knownValue = m.knownValue;
            if (m.getSender().equals(knowledgeResistor.equals(Terminal.SECOND.getName()))) {
                direction = DirectionRequest.opposite(direction);
                knownValue = -knownValue;
            }

            if (knowledgeResistor.getI().equals(knownValue)) {
                if (knowledgeResistor.getWorstIntensityCriticality() < m.criticality) {
                    knowledgeResistor.setWorstIntensityCriticality(m.criticality);
                    knowledgeResistor.setIntensityDirection(direction);
                    // logger.debug("received I" + idr.direction + " from "
                    // + idr.getSender());
                }
            }
        });

    SkillPlot<KnowledgePlot> skillPlot = this.commonFeatures.getFeaturePlot().getSkill();

    String id = this.commonFeatures.getFeatureBasic().getKnowledge().getId();

    this.primaryFeature.getSkill().publishValues(skillPlot,id);

    // send message to terminals (in order to be added in their
    // neighborhood)

    if(this.primaryFeature.getKnowledge().getFirstPotential()==null||this.primaryFeature.getKnowledge().getSecondPotential()==null)

    {
        this.commonFeatures.getFeatureSocial().getSkill()
            .sendPort(this.commonFeatures.getFeatureBasic().getKnowledge().getId());
    }

    KnowledgeSocial knowledgeSocial = this.commonFeatures.getFeatureSocial()
        .getKnowledge();this.primaryFeature.getSkill().communicateIntensity(knowledgeSocial,id);this.primaryFeature.getSkill().requetPotentialUpdate(knowledgeSocial,id);
}}
