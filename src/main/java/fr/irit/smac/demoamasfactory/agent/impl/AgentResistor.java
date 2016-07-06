package fr.irit.smac.demoamasfactory.agent.impl;

import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.amasfactory.agent.features.social.impl.KnowledgeSocial;
import fr.irit.smac.amasfactory.agent.impl.Agent;
import fr.irit.smac.amasfactory.message.EMessageType;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.amasfactory.message.Message;
import fr.irit.smac.demoamasfactory.agent.features.MyFeatures;
import fr.irit.smac.demoamasfactory.agent.features.dipole.KnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.SkillResistor;
import fr.irit.smac.demoamasfactory.agent.features.plot.KnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.SkillPlot;
import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge.Terminal;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public class AgentResistor<F extends MyFeatures, K extends KnowledgeResistor, S extends SkillResistor<K>, P extends Feature<K, S>>
    extends Agent<F, K, S, P>implements ITwoStepsAgent {

    public AgentResistor() {
    }

    @Override
    public void perceive() {

        this.getPrimaryFeature().getSkill().processMsg(this.commonFeatures.getFeatureSocial().getKnowledge());

        // this.commonFeatures.getFeatureSocial().getKnowledge().getMsgBox().getMsgs()
        // .forEach(m -> this.primaryFeature.getSkill().processMsg(m));
    }

    @Override
    public void decideAndAct() {
        
        SkillPlot<KnowledgePlot> skillPlot = this.commonFeatures.getFeaturePlot().getSkill();

        String id = this.commonFeatures.getFeatureBasic().getKnowledge().getId();

        this.primaryFeature.getSkill().publishValues(skillPlot, id);

        // send message to terminals (in order to be added in their
        // neighborhood)

        if (this.primaryFeature.getKnowledge().getFirstPotential() == null
            || this.primaryFeature.getKnowledge().getSecondPotential() == null) {
            this.commonFeatures.getFeatureSocial().getSkill()
                .sendPort(this.commonFeatures.getFeatureBasic().getKnowledge().getId());
        }

        KnowledgeSocial knowledgeSocial = this.commonFeatures.getFeatureSocial().getKnowledge();
        this.primaryFeature.getSkill().communicateIntensity(knowledgeSocial, id);
        this.primaryFeature.getSkill().requetPotentialUpdate(knowledgeSocial, id);
    }
}
