package fr.irit.smac.demoamasfactory.agent.features.node.impl;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.features.social.ISkillSocial;
import fr.irit.smac.amasfactory.agent.impl.Skill;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.impl.Intensity;
import fr.irit.smac.demoamasfactory.agent.features.node.IKnowledgeNode;
import fr.irit.smac.demoamasfactory.agent.features.node.ISkillNode;
import fr.irit.smac.demoamasfactory.agent.features.node.IKnowledgeNode.ENode;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;
import fr.irit.smac.libs.tooling.avt.EFeedback;

public class SkillNode<K extends IKnowledgeNode> extends Skill<K>implements ISkillNode<K> {

    @Override
    public void processMsg(ISkillSocial<IKnowledgeSocial> skillSocial, IMessage message) {

        skillSocial.processMsg(message);
    }

    @Override
    public void publishValue(ISkillPlot<IKnowledgePlot> skillPlot, String id) {

        skillPlot.publish("potential",
            knowledge.getPotential().getValue(), id);
        logger.debug("potential: " +
            knowledge.getPotential().getValue());

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
        for (Double intensity : knowledge.getIntensities().values()) {
            intensitiesSum += intensity;
        }

        if (!knowledge.isReceivedPdr() && intensitiesSum != 0) {
            if (knowledge.getPotentialDirection() == null
                && !knowledge.getPreviousISumChange().equals(intensitiesSum)) {
                logger.debug("Isum: " + intensitiesSum + " previousChange: "
                    + knowledge.getPreviousISumChange());
                knowledge.setPreviousISumChange(intensitiesSum);
                if (intensitiesSum > 0) {
                    knowledge.getPotential().adjustValue(EFeedback.GREATER);
                }
                else if (intensitiesSum < 0) {
                    knowledge.getPotential().adjustValue(EFeedback.LOWER);
                }
            }
        }

    }

    @Override
    public void adjustPotential() {

        if (knowledge.getPotentialDirection() != null) {

            logger.debug("potential=" + knowledge.getPotential().getValue() + " adjust it "
                + knowledge.getPotentialDirection());
            knowledge.getPotential().adjustValue(knowledge.getPotentialDirection());
        }
    }

    @Override
    public void cleanKnowledge() {

        knowledge.setPotentialDirection(null);
        knowledge.setWorstPotentialCriticality(0d);
        knowledge.setReceivedPdr(false);
    }

    @Override
    public void handlePotentialDirectionRequestMessage(IKnowledgeSocial knowledgeSocial) {

        knowledgeSocial.getPortMap().get(ENode.PORT.getName()).getValue().iterator().forEachRemaining(o -> {
            if (o instanceof PotentialDirection) {
                PotentialDirection p = (PotentialDirection) o;
                knowledge.setReceivedPdr(true);
                if (p.knownValue.equals(knowledge.getPotential().getValue())
                    && knowledge.getWorstPotentialCriticality() < p.criticality) {
                    // logger.debug("received " + pdr);
                    knowledge.setWorstPotentialCriticality(p.criticality);
                    knowledge.setPotentialDirection(p.direction);
                }
            }
        });

    }

    @Override
    public void handleIntensityMessage(IKnowledgeSocial knowledgeSocial) {

        knowledgeSocial.getPortMap().get(ENode.PORT.getName()).getValue().iterator().forEachRemaining(intensity -> {
            if (intensity instanceof Intensity) {
                Intensity i = (Intensity) intensity;

                knowledge.getIntensities().put(i.getSender(),
                    i.getValue());
            }
        });

    }
}
