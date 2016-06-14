package fr.irit.smac.demoamasfactory.agent.impl;

import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge.Terminal;
import fr.irit.smac.demoamasfactory.message.impl.Message;
import fr.irit.smac.demoamasfactory.message.impl.PotentialDirectionRequest;
import fr.irit.smac.libs.tooling.avt.EFeedback;

public class UGeneratorBehaviorImpl extends AbstractDipoleBehavior {

	public UGeneratorBehaviorImpl() {
	}
	

	@Override
    public void decideAndAct() {
		// monitor agent
		monitor.publish("tension", knowledge.getU());
		if (knowledge.getV(Terminal.FIRST) != null) {
			monitor.publish("firstPotential",
					knowledge.getV(Terminal.FIRST));
		}
		if (knowledge.getV(Terminal.SECOND) != null) {
			monitor.publish("secondPotential",
					knowledge.getV(Terminal.SECOND));
		}
		logger.debug(knowledge.toString());

		if (knowledge.getV(Terminal.FIRST) == null
				|| knowledge.getV(Terminal.SECOND) == null) {
			// send message to terminals (in order to be added in their
			// neighborhood)
			Message msg = new Message(knowledge.getId());
			msgBox.send(msg, knowledge.getId(Terminal.FIRST));
			msgBox.send(msg, knowledge.getId(Terminal.SECOND));
		} else {
			// compare actual voltage with generator tension
			Double actualVoltage = knowledge.getV(Terminal.SECOND)
					- knowledge.getV(Terminal.FIRST);
			Double error = knowledge.getU() - actualVoltage;
			if (error > 0) {
				// ask to increase the actualVoltage
				requestUChange(Terminal.FIRST, Terminal.SECOND);
			} else if (error < 0) {
				// ask to decrease the actualVoltage
				requestUChange(Terminal.SECOND, Terminal.FIRST);
			}
		}
	}

	private void requestUChange(Terminal lowerReceiver, Terminal greaterReceiver) {
		Message greaterMsg = new PotentialDirectionRequest(knowledge.getId(),
				EFeedback.GREATER, 100d, knowledge.getV(greaterReceiver));
		Message lowerMsg = new PotentialDirectionRequest(knowledge.getId(),
				EFeedback.LOWER, 100d, knowledge.getV(lowerReceiver));
		msgBox.send(greaterMsg,
				knowledge.getId(greaterReceiver));
		msgBox.send(lowerMsg, knowledge.getId(lowerReceiver));
		logger.debug(
				"Sent msg: UP: " + knowledge.getId(greaterReceiver)
						+ "  DOWN: " + knowledge.getId(lowerReceiver));
	}

    @Override
    public void perceive() {
    }

}
