package fr.irit.smac.demoamasfactory.agent.impl;

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

public class AgentNode<F extends MyFeatures> extends Agent<F>implements ITwoStepsAgent {

    public AgentNode() {

    }

    private void processMsg(IMessage m) {

        KnowledgeNode knowledgeNode = this.features.getFeatureNode().getKnowledge();

        if (m instanceof Message) {
            knowledgeNode.neighbors.add(m.getSender());

        }
        if (m instanceof PotentialDirectionRequest) {
            PotentialDirectionRequest pdr = (PotentialDirectionRequest) m;
            this.features.getFeatureNode().getKnowledge().setReceivedPdr(true);
            if (pdr.knownValue.equals(knowledgeNode.potential.getValue())
                && knowledgeNode.worstPotentialCriticality < pdr.criticality) {
                logger.debug("received " + pdr);
                knowledgeNode.worstPotentialCriticality = pdr.criticality;
                knowledgeNode.potentialDirection = pdr.direction;
            }
        }
        else if (m instanceof IntensityMsg) {
            IntensityMsg im = (IntensityMsg) m;
            knowledgeNode.intensities.put(im.getSender(), im.getValue());
        }
    }

    @Override
    public void perceive() {

        IMsgBox<IMessage> msgBox = this.features.getFeatureSocial().getKnowledge().getMsgBox();

        msgBox.getMsgs().forEach(m -> processMsg(m));

        this.features.getFeatureNode().getSkill().publishValue(this.features.getFeaturePlot().getSkill(),
            this.features.getFeatureBasic().getKnowledge().getId());
    }

    @Override
    public void decideAndAct() {

        SkillNode<KnowledgeNode> skillNode = this.features.getFeatureNode().getSkill();
        skillNode.applyKirchhoffLaw();
        skillNode.adjustPotential();

        IMsgBox<IMessage> msgBox = this.features.getFeatureSocial().getKnowledge().getMsgBox();
        KnowledgeNode knowledgeNode = this.features.getFeatureNode().getKnowledge();
        PotentialMsg message = new PotentialMsg(id, knowledgeNode.potential.getValue());
        for (String receiver : knowledgeNode.neighbors) {
            msgBox.send(message, receiver);
        }
        
        skillNode.cleanKnowledge();
        

    }

}
