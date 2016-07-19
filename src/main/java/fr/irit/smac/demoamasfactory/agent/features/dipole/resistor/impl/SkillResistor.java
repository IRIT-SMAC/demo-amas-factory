package fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.impl;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.features.social.ISkillSocial;
import fr.irit.smac.amasfactory.agent.features.social.ITarget;
import fr.irit.smac.amasfactory.message.ValuePortMessage;
import fr.irit.smac.demoamasfactory.agent.features.dipole.IKnowledgeDipole.ETerminal;
import fr.irit.smac.demoamasfactory.agent.features.dipole.impl.SkillDipole;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.IKnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.ISkillResistor;
import fr.irit.smac.demoamasfactory.agent.features.node.IKnowledgeNode.ENode;
import fr.irit.smac.demoamasfactory.agent.features.node.impl.PotentialDirection;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;
import fr.irit.smac.libs.tooling.avt.EFeedback;

public class SkillResistor<K extends IKnowledgeResistor> extends SkillDipole<K>implements ISkillResistor<K> {

    @Override
    public void publishValues(ISkillPlot<IKnowledgePlot> skillPlot, String id) {

        if (knowledge.getR() != null) {
            skillPlot.publish("resistor", knowledge.getR(), id);
        }
        if (knowledge.getI() != null) {
            skillPlot.publish("intensity", knowledge.getI(), id);
        }
        if (knowledge.getU() != null) {
            skillPlot.publish("tension", knowledge.getU(), id);
        }
        logger.debug(knowledge.toString());
    }

    @Override
    public void communicateIntensity(IKnowledgeSocial knowledgeSocial, ISkillSocial<IKnowledgeSocial> skillSocial,
        String id) {

        final Double intensity = knowledge.getI();

        if (intensity != null) {

            Intensity firstMsg = new Intensity(intensity, id);
            ITarget target = knowledgeSocial.getTargetMap().get(ETerminal.FIRST.getName() + "Intensity");
            String agentId = target.getAgentId();
            String portTarget = target.getPortTarget();
            knowledgeSocial.getMsgBox().send(new ValuePortMessage(portTarget, firstMsg, id), agentId);

            Intensity secondMsg = new Intensity(-intensity, id);
            ITarget target2 = knowledgeSocial.getTargetMap().get(ETerminal.SECOND.getName() + "Intensity");
            String agentId2 = target2.getAgentId();
            String portTarget2 = target2.getPortTarget();
            knowledgeSocial.getMsgBox().send(new ValuePortMessage(portTarget2, secondMsg, id), agentId2);
        }
    }

    @Override
    public void requestPotentialUpdate(IKnowledgeSocial knowledgeSocial, ISkillSocial<IKnowledgeSocial> skillSocial,
        String id) {

        EFeedback intensityDirection = knowledge.getIntensityDirection();
        Double worstIntensityCriticality = knowledge.getWorstIntensityCriticality();

        if (intensityDirection != null) {
            PotentialDirection firstMsg = new PotentialDirection(
                PotentialDirection.opposite(intensityDirection), worstIntensityCriticality,
                knowledge.getFirstPotential(), id);

            knowledgeSocial.getTargetMap().get(ETerminal.FIRST.getName()).setValue(firstMsg);

            logger
                .debug("send V" + PotentialDirection.opposite(intensityDirection) + " to " + ETerminal.FIRST.getName());

            PotentialDirection secondMsg = new PotentialDirection(
                intensityDirection, worstIntensityCriticality, knowledge.getSecondPotential(), id);
            knowledgeSocial.getTargetMap().get(ETerminal.SECOND.getName()).setValue(secondMsg);

            logger.debug("send V" + intensityDirection + " to " + ETerminal.SECOND.getName());

            skillSocial.sendValueToTargets(id);

            knowledge.setWorstIntensityCriticality(0d);
            knowledge.setIntensityDirection(null);
        }
    }

}
