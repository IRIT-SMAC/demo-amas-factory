package fr.irit.smac.demoamasfactory.agent.features.plot;

import fr.irit.smac.amasfactory.agent.ISkill;

public interface ISkillPlot<K extends IKnowledgePlot> extends ISkill<K> {

    public void publish(String name, Double value, String agentId);
}
