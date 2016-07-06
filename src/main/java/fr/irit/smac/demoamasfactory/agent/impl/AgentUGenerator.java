package fr.irit.smac.demoamasfactory.agent.impl;

import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.amasfactory.agent.features.social.impl.KnowledgeSocial;
import fr.irit.smac.amasfactory.agent.features.social.impl.SkillSocial;
import fr.irit.smac.amasfactory.agent.impl.Agent;
import fr.irit.smac.amasfactory.message.ValuePortMessage;
import fr.irit.smac.demoamasfactory.agent.features.MyFeatures;
import fr.irit.smac.demoamasfactory.agent.features.dipole.KnowledgeUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.dipole.SkillUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.plot.KnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.SkillPlot;
import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge.Terminal;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public class AgentUGenerator<F extends MyFeatures, K extends KnowledgeUGenerator, S extends SkillUGenerator<K>, P extends Feature<K, S>>
    extends Agent<F, K, S, P>implements ITwoStepsAgent {

    public AgentUGenerator() {
    }

    @Override
    public void perceive() {

        KnowledgeSocial knowledgeSocial = this.commonFeatures.getFeatureSocial().getKnowledge();
        knowledgeSocial.getMsgBox().getMsgs()
            .forEach(m -> this.primaryFeature.getSkill().processMsg(m, knowledgeSocial));
    }

    @Override
    public void decideAndAct() {

        KnowledgeUGenerator knowledgeUGenerator = this.primaryFeature.getKnowledge();
        
        this.commonFeatures.getFeatureSocial().getKnowledge().getValuePortMessageCollection().forEach(m -> {
            ValuePortMessage message = (ValuePortMessage) m;
            if (message.getPort().equals(Terminal.FIRST.getName())) {
                knowledgeUGenerator.setFirstPotential((Double) message.getValue());
            }
            else if (message.getPort().equals(Terminal.SECOND.getName())) {
                knowledgeUGenerator.setSecondPotential((Double) message.getValue());
            }
        });
        
        SkillPlot<KnowledgePlot> skillPlot = this.commonFeatures.getFeaturePlot().getSkill();

        String id = this.commonFeatures.getFeatureBasic().getKnowledge().getId();

        this.primaryFeature.getSkill().publishValues(skillPlot, id);
        // logger.debug(knowledge.toString());

        SkillSocial<KnowledgeSocial> skillSocial = this.commonFeatures.getFeatureSocial().getSkill();
        KnowledgeSocial knowledgeSocial = this.commonFeatures.getFeatureSocial().getKnowledge();
        skillSocial.updatePortFromMessage();

        if (this.primaryFeature.getKnowledge().getFirstPotential() == null
            || this.primaryFeature.getKnowledge().getSecondPotential() == null) {
            skillSocial.sendPort(this.commonFeatures.getFeatureBasic().getKnowledge().getId());
        }

        else {
            this.primaryFeature.getSkill().compareVoltageWithGeneratorTension(knowledgeSocial, id);
        }
    }
}
