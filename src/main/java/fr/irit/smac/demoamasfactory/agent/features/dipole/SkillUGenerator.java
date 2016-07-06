package fr.irit.smac.demoamasfactory.agent.features.dipole;

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
        if (knowledge.getV(Terminal.FIRST) != null) {
            skillPlot.publish("firstPotential",
                knowledge.getV(Terminal.FIRST), id);
        }
        if (knowledge.getV(Terminal.SECOND) != null) {
            skillPlot.publish("secondPotential",
                knowledge.getV(Terminal.SECOND), id);
        }
    }

    public void compareVoltageWithGeneratorTension(IMsgBox<IMessage> msgBox, String id) {

        // compare actual voltage with generator tension
        Double actualVoltage = knowledge.getV(Terminal.SECOND)
            - knowledge.getV(Terminal.FIRST);
        Double error = knowledge.getU() - actualVoltage;
        if (error > 0) {
            // ask to increase the actualVoltage
            requestUChange(Terminal.FIRST, Terminal.SECOND, id, msgBox);
        }
        else if (error < 0) {
            // ask to decrease the actualVoltage
            requestUChange(Terminal.SECOND, Terminal.FIRST, id, msgBox);
        }
    }

    private void requestUChange(Terminal lowerReceiver, Terminal greaterReceiver, String id, IMsgBox<IMessage> msgBox) {

        Message greaterMsg = new PotentialDirectionRequest(id,
            EFeedback.GREATER, 100d, knowledge.getV(greaterReceiver));
        Message lowerMsg = new PotentialDirectionRequest(id,
            EFeedback.LOWER, 100d, knowledge.getV(lowerReceiver));
        msgBox.send(greaterMsg,
            knowledge.getId(greaterReceiver));
        msgBox.send(lowerMsg, knowledge.getId(lowerReceiver));
//        logger.debug(
//            "Sent msg: UP: " + knowledge.getId(greaterReceiver)
//                + "  DOWN: " + knowledge.getId(lowerReceiver));
    }
}
