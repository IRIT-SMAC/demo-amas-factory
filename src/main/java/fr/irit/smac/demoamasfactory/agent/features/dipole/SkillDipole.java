package fr.irit.smac.demoamasfactory.agent.features.dipole;

import fr.irit.smac.amasfactory.agent.features.social.impl.KnowledgeSocial;
import fr.irit.smac.amasfactory.agent.impl.Skill;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.amasfactory.message.PortOfTargetMessage;
import fr.irit.smac.amasfactory.message.ValuePortMessage;

public class SkillDipole<K extends KnowledgeDipole> extends Skill<K> {

    public void processMsg(IMessage m, KnowledgeSocial knowledgeSocial) {

        if (m instanceof ValuePortMessage) {
            knowledgeSocial.getValuePortMessageCollection().add((ValuePortMessage) m);
        }
        else if (m instanceof PortOfTargetMessage) {
            knowledgeSocial.getPortOfTargetMessageCollection().add((PortOfTargetMessage) m);
        }
    }
}
