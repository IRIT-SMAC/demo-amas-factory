package fr.irit.smac.demoamasfactory.agent.features.dipole;

import fr.irit.smac.amasfactory.agent.impl.Skill;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge.Terminal;
import fr.irit.smac.demoamasfactory.message.impl.PotentialMsg;

public class SkillDipole<K extends KnowledgeDipole> extends Skill<K>{

    public void processMsg(IMessage m) {
        
        if (m instanceof PotentialMsg) {
            PotentialMsg pm = (PotentialMsg) m;
            Terminal terminal = knowledge.getTerminal(pm.getSender());
            if (terminal != null) {
                knowledge.setTerminalV(terminal, pm.getValue());
            }
        }
    }
}
 