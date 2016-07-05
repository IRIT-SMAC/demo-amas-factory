package fr.irit.smac.demoamasfactory.agent.features.plot;

import fr.irit.smac.amasfactory.agent.impl.Skill;
import fr.irit.smac.libs.tooling.plot.server.AgentPlotChart;

public class SkillPlot<K extends KnowledgePlot> extends Skill<K> {

    public void publish(String name, Double value, String agentId) {
        if (this.knowledge.getAgentsFilter().test(agentId) && this.knowledge.getValuesFilter().test(name)) {

            if (this.knowledge.getChart() == null) {
                this.knowledge.setChart(new AgentPlotChart(agentId));
            }
            this.knowledge.getChart().add(name, value);
        }
    }
}
