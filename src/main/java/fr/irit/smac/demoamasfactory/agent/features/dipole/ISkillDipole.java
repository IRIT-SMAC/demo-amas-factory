package fr.irit.smac.demoamasfactory.agent.features.dipole;

import fr.irit.smac.amasfactory.agent.ISkill;
import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.message.IMessage;

public interface ISkillDipole<K extends IKnowledgeDipole> extends ISkill<K>{

    public void processMsg(IMessage m, IKnowledgeSocial knowledgeSocial);
}
