package fr.irit.smac.demoamasfactory.agent.features.dipole;

import fr.irit.smac.amasfactory.agent.features.social.impl.KnowledgeSocial;
import fr.irit.smac.amasfactory.agent.impl.Skill;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.amasfactory.message.PortOfTargetMessage;
import fr.irit.smac.amasfactory.message.ValuePortMessage;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;

public class SkillDipole<K extends KnowledgeDipole> extends Skill<K> {

    public void processMsg(KnowledgeSocial knowledgeSocial) {
        
        IMsgBox<IMessage> msgBox = knowledgeSocial.getMsgBox();
        for (IMessage demoMessage : msgBox.getMsgs()) {
            if (demoMessage instanceof ValuePortMessage) {
                knowledgeSocial.getValuePortMessageCollection().add((ValuePortMessage) demoMessage);
                ValuePortMessage message = (ValuePortMessage) demoMessage;
                if (message.getPort().equals("firstTerminal")) {
                    knowledge.setFirstPotential((Double) message.getValue());
                } else if (message.getPort().equals("secondTerminal")){
                    knowledge.setSecondPotential((Double) message.getValue());
                }
                
            } else if (demoMessage instanceof PortOfTargetMessage) {
                knowledgeSocial.getPortOfTargetMessageCollection().add((PortOfTargetMessage) demoMessage);
            }
        }
    }
}
