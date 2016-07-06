package fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.impl;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.agent.features.dipole.IKnowledgeDipole.Terminal;
import fr.irit.smac.demoamasfactory.agent.features.dipole.impl.SkillDipole;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.IKnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.ISkillResistor;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;
import fr.irit.smac.demoamasfactory.message.impl.DirectionRequest;
import fr.irit.smac.demoamasfactory.message.impl.IntensityDirectionRequest;
import fr.irit.smac.demoamasfactory.message.impl.IntensityMsg;
import fr.irit.smac.demoamasfactory.message.impl.PotentialDirectionRequest;
import fr.irit.smac.libs.tooling.avt.EFeedback;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;

public class SkillResistor<K extends IKnowledgeResistor> extends SkillDipole<K>implements ISkillResistor<K> {

    @Override
    public void processMsg(IMessage message, IKnowledgeSocial knowledgeSocial) {
        super.processMsg(message, knowledgeSocial);

        if (message instanceof IntensityDirectionRequest) {
            this.knowledge.getIntensityDirectionRequest().add((IntensityDirectionRequest) message);
        }
    }

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
    public void communicateIntensity(IKnowledgeSocial knowledgeSocial, String id) {

        IMsgBox<IMessage> msgBox = knowledgeSocial.getMsgBox();
        final Double intensity = knowledge.getI();
        if (intensity != null) {
            IntensityMsg firstMsg = new IntensityMsg(id, intensity);
            msgBox.send(firstMsg, knowledgeSocial.getPortMap().get(Terminal.FIRST.getName()).getId());
            IntensityMsg secondMsg = new IntensityMsg(id, -intensity);
            msgBox.send(secondMsg, knowledgeSocial.getPortMap().get(Terminal.SECOND.getName()).getId());
        }
    }

    @Override
    public void requetPotentialUpdate(IKnowledgeSocial knowledgeSocial, String id) {

        IMsgBox<IMessage> msgBox = knowledgeSocial.getMsgBox();
        EFeedback intensityDirection = knowledge.getIntensityDirection();
        Double worstIntensityCriticality = knowledge.getWorstIntensityCriticality();
        if (intensityDirection != null) {
            PotentialDirectionRequest firstMsg = new PotentialDirectionRequest(id,
                DirectionRequest.opposite(intensityDirection), worstIntensityCriticality,
                knowledge.getFirstPotential());
            msgBox.send(firstMsg, knowledgeSocial.getPortMap().get(Terminal.FIRST.getName()).getId());
            logger.debug("send V" +
                DirectionRequest.opposite(intensityDirection) + " to "
                + knowledgeSocial.getPortMap().get(Terminal.FIRST.getName()));
            PotentialDirectionRequest secondMsg = new PotentialDirectionRequest(id,
                intensityDirection, worstIntensityCriticality, knowledge.getSecondPotential());
            msgBox.send(secondMsg, knowledgeSocial.getPortMap().get(Terminal.SECOND.getName()).getId());
            logger.debug("send V" + intensityDirection + " to " +
                knowledgeSocial.getPortMap().get(Terminal.SECOND.getName()));

            // clear request
            worstIntensityCriticality = 0d;
            intensityDirection = null;
        }
    }

}
