package fr.irit.smac.demoamasfactory.agent.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge;
import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge.Terminal;
import fr.irit.smac.demoamasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.message.impl.PotentialMsg;

public abstract class AbstractDipoleBehavior extends AbstractAgent {

    @JsonProperty
    protected IDipoleKnowledge knowledge;

    public AbstractDipoleBehavior() {
    }

    protected void processMsg(IMessage m) {
        if (m instanceof PotentialMsg) {
            PotentialMsg pm = (PotentialMsg) m;
            Terminal terminal = knowledge.getTerminal(pm.getSender());
            if (terminal != null) {
                knowledge.setTerminalV(terminal, pm.getValue());
            }
        }
    }
}