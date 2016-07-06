package fr.irit.smac.demoamasfactory.agent.features.plot;

import java.util.function.Predicate;

import fr.irit.smac.amasfactory.agent.impl.Knowledge;
import fr.irit.smac.libs.tooling.plot.server.AgentPlotChart;

public class KnowledgePlot extends Knowledge {

    String            agentId;
    AgentPlotChart    chart;
    Predicate<String> agentsFilter = s -> true;
    Predicate<String> valuesFilter = s -> true;

    public void KnowledgePlot() {

        this.agentsFilter = (s -> "R 5_5|5_6".equals(s) || "5_5".equals(s) || "1_1".equals(s)
            || "10_10".equals(s) || "gen 20V".equals(s) || "gen 2".equals(s));
        this.chart = new AgentPlotChart(agentId);
    }

    public void KnowledgePlot(String id, Predicate<String> agentsFilter, Predicate<String> valuesFilter) {
        this.agentId = id;
        this.agentsFilter = (s -> "R 5_5|5_6".equals(s) || "5_5".equals(s) || "1_1".equals(s)
            || "10_10".equals(s) || "gen 20V".equals(s) || "gen 2".equals(s));
        this.valuesFilter = valuesFilter;
        this.chart = new AgentPlotChart(agentId);
    }

    public Predicate<String> getValuesFilter() {
        return valuesFilter;
    }

    public void setAgentsFilter(Predicate<String> agentsFilter) {
        this.agentsFilter = agentsFilter;
    }

    public Predicate<String> getAgentsFilter() {
        return agentsFilter;
    }

    public AgentPlotChart getChart() {
        return chart;
    }

    public void setChart(AgentPlotChart chart) {
        this.chart = chart;
    }
}
