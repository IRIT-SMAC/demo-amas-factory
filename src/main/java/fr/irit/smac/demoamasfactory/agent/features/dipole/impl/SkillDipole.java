package fr.irit.smac.demoamasfactory.agent.features.dipole.impl;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.impl.Skill;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.amasfactory.message.PortOfTargetMessage;
import fr.irit.smac.amasfactory.message.ValuePortMessage;
import fr.irit.smac.demoamasfactory.agent.features.dipole.IKnowledgeDipole;
import fr.irit.smac.demoamasfactory.agent.features.dipole.ISkillDipole;

public class SkillDipole<K extends IKnowledgeDipole> extends Skill<K> implements ISkillDipole<K>{

    @Override
    public void processMsg(IMessage m, IKnowledgeSocial knowledgeSocial) {

        if (m instanceof ValuePortMessage) {
            knowledgeSocial.getSendToTargetMessageCollection().add((ValuePortMessage) m);
        }
        else if (m instanceof PortOfTargetMessage) {
            knowledgeSocial.getSendPortToTargetMessageCollection().add((PortOfTargetMessage) m);
        }
    }
}
