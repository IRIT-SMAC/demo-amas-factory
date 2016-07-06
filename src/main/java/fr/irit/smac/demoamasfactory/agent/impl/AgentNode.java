package fr.irit.smac.demoamasfactory.agent.impl;

import ch.qos.logback.core.util.SystemInfo;
import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.amasfactory.agent.features.social.ITarget;
import fr.irit.smac.amasfactory.agent.features.social.impl.KnowledgeSocial;
import fr.irit.smac.amasfactory.agent.features.social.impl.Target;
import fr.irit.smac.amasfactory.agent.impl.Agent;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.amasfactory.message.Message;
import fr.irit.smac.amasfactory.message.PortOfTargetMessage;
import fr.irit.smac.amasfactory.message.ValuePortMessage;
import fr.irit.smac.demoamasfactory.agent.features.MyFeatures;
import fr.irit.smac.demoamasfactory.agent.features.node.KnowledgeNode;
import fr.irit.smac.demoamasfactory.agent.features.node.SkillNode;
import fr.irit.smac.demoamasfactory.message.impl.IntensityMsg;
import fr.irit.smac.demoamasfactory.message.impl.PotentialDirectionRequest;
import fr.irit.smac.demoamasfactory.message.impl.PotentialMsg;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public class AgentNode<F extends MyFeatures, K extends KnowledgeNode, S extends SkillNode<K>, P extends Feature<K, S>>
    extends Agent<F, K, S, P>
    implements ITwoStepsAgent {

    public AgentNode() {

    }

    private void processMsg(KnowledgeSocial knowledgeSocial) {

        KnowledgeNode knowledge = this.primaryFeature.getKnowledge();

        IMsgBox<IMessage> msgBox = knowledgeSocial.getMsgBox();
        for (IMessage demoMessage : msgBox.getMsgs()) {

            if (demoMessage instanceof ValuePortMessage) {
                knowledgeSocial.getValuePortMessageCollection().add((ValuePortMessage) demoMessage);
            }
            else if (demoMessage instanceof PortOfTargetMessage) {
                knowledgeSocial.getPortOfTargetMessageCollection().add((PortOfTargetMessage) demoMessage);
                Target target = new Target(((PortOfTargetMessage) demoMessage).getSender(), ((PortOfTargetMessage) demoMessage).getPortSource(), null);
                this.commonFeatures.getFeatureSocial().getKnowledge().getTargetSet().add(target);
            }
            else if (demoMessage instanceof PotentialDirectionRequest) {
                PotentialDirectionRequest pdr = (PotentialDirectionRequest) demoMessage;
                this.primaryFeature.getKnowledge().setReceivedPdr(true);
                if (pdr.knownValue.equals(knowledge.potential.getValue())
                    && knowledge.worstPotentialCriticality < pdr.criticality) {
                    logger.debug("received " + pdr);
                    knowledge.worstPotentialCriticality = pdr.criticality;
                    knowledge.potentialDirection = pdr.direction;
                }
            }
            else if (demoMessage instanceof IntensityMsg) {
                IntensityMsg im = (IntensityMsg) demoMessage;
                knowledge.intensities.put(im.getSender(), im.getValue());
            }
        }

        // KnowledgeNode knowledge = this.primaryFeature.getKnowledge();
        // if (m instanceof Message) {
        // knowledge.neighbors.add(m.getSender());
        //
        // }
        // if (m instanceof PotentialDirectionRequest) {
        // PotentialDirectionRequest pdr = (PotentialDirectionRequest) m;
        // this.primaryFeature.getKnowledge().setReceivedPdr(true);
        // if (pdr.knownValue.equals(knowledge.potential.getValue())
        // && knowledge.worstPotentialCriticality < pdr.criticality) {
        // logger.debug("received " + pdr);
        // knowledge.worstPotentialCriticality = pdr.criticality;
        // knowledge.potentialDirection = pdr.direction;
        // }
        // }
        // else if (m instanceof IntensityMsg) {
        // IntensityMsg im = (IntensityMsg) m;
        // knowledge.intensities.put(im.getSender(), im.getValue());
        // }
    }

    @Override
    public void perceive() {

        IMsgBox<IMessage> msgBox = this.commonFeatures.getFeatureSocial().getKnowledge().getMsgBox();

        this.processMsg(this.commonFeatures.getFeatureSocial().getKnowledge());
        // msgBox.getMsgs().forEach(m -> processMsg(m));

        this.primaryFeature.getSkill().publishValue(this.commonFeatures.getFeaturePlot().getSkill(),
            this.commonFeatures.getFeatureBasic().getKnowledge().getId());
    }

    @Override
    public void decideAndAct() {

        SkillNode<K> skill = this.primaryFeature.getSkill();
        skill.applyKirchhoffLaw();
        skill.adjustPotential();

        KnowledgeNode knowledge = this.primaryFeature.getKnowledge();
        this.commonFeatures.getFeatureSocial().getKnowledge().setOutputValue(knowledge.potential.getValue());
        this.commonFeatures.getFeatureSocial().getSkill().sendOutputValue(this.id);

        skill.cleanKnowledge();

    }

}
