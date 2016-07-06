package fr.irit.smac.demoamasfactory.agent.features.node;

import fr.irit.smac.amasfactory.agent.ISkill;
import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;

public interface ISkillNode<K extends IKnowledgeNode> extends ISkill<K> {

    public void applyKirchhoffLaw();

    public void processMsg(IMessage message, IKnowledgeSocial knowledgeSocial);

    public void publishValue(ISkillPlot<IKnowledgePlot> skillPlot, String id);

    public void adjustPotential();

    public void cleanKnowledge();

}
