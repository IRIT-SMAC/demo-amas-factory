package fr.irit.smac.demoamasfactory.agent.features.dipole;

import fr.irit.smac.amasfactory.agent.features.social.impl.KnowledgeSocial;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.amasfactory.message.Message;
import fr.irit.smac.demoamasfactory.agent.features.plot.KnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.SkillPlot;
import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge.Terminal;
import fr.irit.smac.demoamasfactory.message.impl.PotentialDirectionRequest;
import fr.irit.smac.libs.tooling.avt.EFeedback;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;

public class SkillUGenerator<F extends KnowledgeUGenerator> extends SkillDipole<F> {

    public void publishValues(SkillPlot<KnowledgePlot> skillPlot, String id) {

        skillPlot.publish("tension", knowledge.getU(), id);
        if (knowledge.getFirstPotential() != null) {
            skillPlot.publish("firstPotential",
                knowledge.getFirstPotential(), id);
        }
        if (knowledge.getSecondPotential() != null) {
            skillPlot.publish("secondPotential",
                knowledge.getSecondPotential(), id);
        }
    }

    public void compareVoltageWithGeneratorTension(KnowledgeSocial knowledgeSocial, String id) {

        // compare actual voltage with generator tension
        Double actualVoltage = knowledge.getSecondPotential()
            - knowledge.getFirstPotential();
        Double error = knowledge.getU() - actualVoltage;
        if (error > 0) {
            // ask to increase the actualVoltage
            requestUChange(knowledge.getFirstPotential(), knowledge.getSecondPotential(), id,
                knowledgeSocial.getPortMap().get(Terminal.FIRST.getName()).getId(),
                knowledgeSocial.getPortMap().get(Terminal.SECOND.getName()).getId(), knowledgeSocial.getMsgBox());
        }
        else if (error < 0) {
            // ask to decrease the actualVoltage
            requestUChange(knowledge.getSecondPotential(), knowledge.getFirstPotential(), id,
                knowledgeSocial.getPortMap().get(Terminal.SECOND.getName()).getId(),
                knowledgeSocial.getPortMap().get(Terminal.FIRST.getName()).getId(), knowledgeSocial.getMsgBox());
        }
    }

    private void requestUChange(Double lowerReceiver, Double greaterReceiver, String id, String idLowerReceiver,
        String idGreaterReceiver, IMsgBox<IMessage> msgBox) {

        Message greaterMsg = new PotentialDirectionRequest(id,
            EFeedback.GREATER, 100d, greaterReceiver);
        Message lowerMsg = new PotentialDirectionRequest(id,
            EFeedback.LOWER, 100d, lowerReceiver);
        msgBox.send(greaterMsg,
            idGreaterReceiver);
        msgBox.send(lowerMsg, idLowerReceiver);
        logger.debug(
            "Sent msg: UP: " + idGreaterReceiver
                + " DOWN: " + idLowerReceiver);
    }
}
