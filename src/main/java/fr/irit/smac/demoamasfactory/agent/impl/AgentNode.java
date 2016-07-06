package fr.irit.smac.demoamasfactory.agent.impl;

import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.amasfactory.agent.impl.Agent;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.amasfactory.message.Message;
import fr.irit.smac.demoamasfactory.agent.features.MyFeatures;
import fr.irit.smac.demoamasfactory.agent.features.node.KnowledgeNode;
import fr.irit.smac.demoamasfactory.agent.features.node.SkillNode;
import fr.irit.smac.demoamasfactory.message.impl.IntensityMsg;
import fr.irit.smac.demoamasfactory.message.impl.PotentialDirectionRequest;
import fr.irit.smac.demoamasfactory.message.impl.PotentialMsg;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public class AgentNode<F extends MyFeatures, P extends Feature<K,S>, K extends KnowledgeNode, S extends SkillNode<K>> extends Agent<F, P, K, S>
    implements ITwoStepsAgent {

    public AgentNode() {

    }

    private void processMsg(IMessage m) {

        KnowledgeNode knowledge = this.primaryFeature.getKnowledge();
        if (m instanceof Message) {
            knowledge.neighbors.add(m.getSender());

        }
        if (m instanceof PotentialDirectionRequest) {
            PotentialDirectionRequest pdr = (PotentialDirectionRequest) m;
            this.primaryFeature.getKnowledge().setReceivedPdr(true);
            if (pdr.knownValue.equals(knowledge.potential.getValue())
                && knowledge.worstPotentialCriticality < pdr.criticality) {
                logger.debug("received " + pdr);
                knowledge.worstPotentialCriticality = pdr.criticality;
                knowledge.potentialDirection = pdr.direction;
            }
        }
        else if (m instanceof IntensityMsg) {
            IntensityMsg im = (IntensityMsg) m;
            knowledge.intensities.put(im.getSender(), im.getValue());
        }
    }

    @Override
    public void perceive() {

        IMsgBox<IMessage> msgBox = this.commonFeatures.getFeatureSocial().getKnowledge().getMsgBox();

        msgBox.getMsgs().forEach(m -> processMsg(m));

        this.primaryFeature.getSkill().publishValue(this.commonFeatures.getFeaturePlot().getSkill(),
            this.commonFeatures.getFeatureBasic().getKnowledge().getId());
    }

    @Override
    public void decideAndAct() {

        SkillNode<K> skill = this.primaryFeature.getSkill();

        skill.applyKirchhoffLaw();
        skill.adjustPotential();

        KnowledgeNode knowledge = this.primaryFeature.getKnowledge();

        IMsgBox<IMessage> msgBox = this.commonFeatures.getFeatureSocial().getKnowledge().getMsgBox();
        PotentialMsg message = new PotentialMsg(id, knowledge.potential.getValue());
        for (String receiver : knowledge.neighbors) {
            msgBox.send(message, receiver);
        }

        skill.cleanKnowledge();

    }

}
