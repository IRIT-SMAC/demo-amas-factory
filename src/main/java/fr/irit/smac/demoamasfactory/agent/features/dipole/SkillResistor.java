package fr.irit.smac.demoamasfactory.agent.features.dipole;

import fr.irit.smac.amasfactory.agent.features.social.impl.KnowledgeSocial;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.agent.features.plot.KnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.SkillPlot;
import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge.Terminal;
import fr.irit.smac.demoamasfactory.message.impl.DirectionRequest;
import fr.irit.smac.demoamasfactory.message.impl.IntensityDirectionRequest;
import fr.irit.smac.demoamasfactory.message.impl.IntensityMsg;
import fr.irit.smac.demoamasfactory.message.impl.PotentialDirectionRequest;
import fr.irit.smac.libs.tooling.avt.EFeedback;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;

public class SkillResistor<K extends KnowledgeResistor> extends SkillDipole<K> {

    @Override
    public void processMsg(IMessage message, KnowledgeSocial knowledgeSocial) {
        super.processMsg(message, knowledgeSocial);

        if (message instanceof IntensityDirectionRequest) {
            this.knowledge.getIntensityDirectionRequest().add((IntensityDirectionRequest) message);
        }
    }

    public void publishValues(SkillPlot<KnowledgePlot> skillPlot, String id) {

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

    public void communicateIntensity(KnowledgeSocial knowledgeSocial, String id) {

        IMsgBox<IMessage> msgBox = knowledgeSocial.getMsgBox();
        final Double intensity = knowledge.getI();
        if (intensity != null) {
            IntensityMsg firstMsg = new IntensityMsg(id, intensity);
            msgBox.send(firstMsg, knowledgeSocial.getPortMap().get(Terminal.FIRST.getName()).getId());
            IntensityMsg secondMsg = new IntensityMsg(id, -intensity);
            msgBox.send(secondMsg, knowledgeSocial.getPortMap().get(Terminal.SECOND.getName()).getId());
        }
    }

    public void requetPotentialUpdate(KnowledgeSocial knowledgeSocial, String id) {

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
