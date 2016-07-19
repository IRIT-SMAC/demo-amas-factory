package fr.irit.smac.demoamasfactory.agent.features.dipole.impl;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.features.social.ISkillSocial;
import fr.irit.smac.amasfactory.agent.impl.Skill;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.agent.features.dipole.IKnowledgeDipole;
import fr.irit.smac.demoamasfactory.agent.features.dipole.ISkillDipole;

public class SkillDipole<K extends IKnowledgeDipole> extends Skill<K>implements ISkillDipole<K> {

    @Override
    public void processMsg(ISkillSocial<IKnowledgeSocial> skillSocial, IMessage message) {

        skillSocial.processMsg(message);
    }
}
