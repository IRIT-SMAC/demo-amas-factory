package fr.irit.smac.demoamasfactory.agent.features.dipole;

import fr.irit.smac.amasfactory.agent.ISkill;
import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.features.social.ISkillSocial;
import fr.irit.smac.amasfactory.message.IMessage;

public interface ISkillDipole<K> extends ISkill<K>{

    public void processMsg(ISkillSocial<IKnowledgeSocial> skillSocial, IMessage message);
}
