package fr.irit.smac.demoamasfactory.agent.features.node.impl;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.impl.Skill;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.amasfactory.message.PortOfTargetMessage;
import fr.irit.smac.amasfactory.message.ValuePortMessage;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.impl.Intensity;
import fr.irit.smac.demoamasfactory.agent.features.node.IKnowledgeNode;
import fr.irit.smac.demoamasfactory.agent.features.node.ISkillNode;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;
import fr.irit.smac.libs.tooling.avt.EFeedback;

public class SkillNode<K extends IKnowledgeNode> extends Skill<K>implements ISkillNode<K> {

    @Override
    public void processMsg(IMessage message, IKnowledgeSocial knowledgeSocial) {

        if (message instanceof ValuePortMessage) {
            knowledgeSocial.getSendToTargetMessageCollection().add((ValuePortMessage) message);
        }
        else if (message instanceof PortOfTargetMessage) {
            knowledgeSocial.getSendPortToTargetMessageCollection().add((PortOfTargetMessage) message);
        }
    }

    @Override
    public void publishValue(ISkillPlot<IKnowledgePlot> skillPlot, String id) {


        skillPlot.publish("potential",
            this.knowledge.getPotential().getValue(), id);
        logger.debug("potential: " +
            this.knowledge.getPotential().getValue());

        if (knowledge.getIntensities().get("R1") != null) {
            skillPlot.publish("R1", knowledge.getIntensities().get("R1"), id);
        }
        if (knowledge.getIntensities().get("R2") != null) {
            skillPlot.publish("R2", knowledge.getIntensities().get("R2"), id);
        }
        if (knowledge.getIntensities().get("R3") != null) {
            skillPlot.publish("R3", knowledge.getIntensities().get("R3"), id);
        }
    }

    @Override
    public void applyKirchhoffLaw() {

        Double intensitiesSum = 0d;
        for (Double intensity : this.knowledge.getIntensities().values()) {
            intensitiesSum += intensity;
        }

        if (!this.knowledge.isReceivedPdr() && intensitiesSum != 0) {
            if (this.knowledge.getPotentialDirection() == null
                && !this.knowledge.getPreviousISumChange().equals(intensitiesSum)) {
                logger.debug("Isum: " + intensitiesSum + " previousChange: "
                    + this.knowledge.getPreviousISumChange());
                this.knowledge.setPreviousISumChange(intensitiesSum);
                if (intensitiesSum > 0) {
                    this.knowledge.getPotential().adjustValue(EFeedback.GREATER);
                }
                else if (intensitiesSum < 0) {
                    this.knowledge.getPotential().adjustValue(EFeedback.LOWER);
                }
            }
        }

    }

    @Override
    public void adjustPotential() {

        if (this.knowledge.getPotentialDirection() != null) {

            logger.debug(
                "potential=" + this.knowledge.getPotential().getValue() + " adjust it "
                    + this.knowledge.getPotentialDirection());
            this.knowledge.getPotential().adjustValue(this.knowledge.getPotentialDirection());
        }
    }

    @Override
    public void cleanKnowledge() {

        this.knowledge.setPotentialDirection(null);
        this.knowledge.setWorstPotentialCriticality(0d);
        this.knowledge.setReceivedPdr(false);
    }

    @Override
    public void handlePotentialDirectionRequestMessage(IKnowledgeSocial knowledgeSocial) {

        knowledgeSocial.getSendToTargetMessageCollection().forEach(m -> {

            if (m.getValue() instanceof PotentialDirection) {
                PotentialDirection p = (PotentialDirection) m.getValue();
                this.knowledge.setReceivedPdr(true);
                if (p.knownValue.equals(this.knowledge.getPotential().getValue())
                    && this.knowledge.getWorstPotentialCriticality() < p.criticality) {
                    // logger.debug("received " + pdr);
                    this.knowledge.setWorstPotentialCriticality(p.criticality);
                    this.knowledge.setPotentialDirection(p.direction);
                }
            }
        });
    }

    @Override
    public void handleIntensityMessage(IKnowledgeSocial knowledgeSocial) {

        knowledgeSocial.getSendToTargetMessageCollection().forEach(m -> {

            if (m.getValue() instanceof Intensity) {
                this.knowledge.getIntensities().put(m.getSender(), ((Intensity) m.getValue()).getValue());
            }
        });

    }
}
