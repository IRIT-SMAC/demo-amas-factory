package fr.irit.smac.demoamasfactory.agent.features.plot.impl;

import fr.irit.smac.amasfactory.agent.impl.Skill;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;

public class SkillPlot<K extends IKnowledgePlot> extends Skill<K>implements ISkillPlot<K> {

    @Override
    public void publish(String name, Double value, String agentId) {

        if (this.knowledge.getAgentsFilter().test(agentId) && this.knowledge.getValuesFilter().test(name)) {
            this.knowledge.getChart().add(name, value);
        }
    }
}
