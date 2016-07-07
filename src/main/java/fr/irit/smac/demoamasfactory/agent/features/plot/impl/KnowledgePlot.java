package fr.irit.smac.demoamasfactory.agent.features.plot.impl;

import java.util.function.Predicate;

import fr.irit.smac.amasfactory.agent.impl.Knowledge;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.libs.tooling.plot.server.AgentPlotChart;

public class KnowledgePlot extends Knowledge implements IKnowledgePlot{

    String            agentId;
    AgentPlotChart    chart;
    Predicate<String> agentsFilter = s -> true;
    Predicate<String> valuesFilter = s -> true;

    public KnowledgePlot() {

    }

    public KnowledgePlot(String id, Predicate<String> agentsFilter, Predicate<String> valuesFilter) {
        this.agentId = id;
        this.agentsFilter = agentsFilter;
        this.valuesFilter = valuesFilter;
        this.chart = new AgentPlotChart(id);
    }

    @Override
    public Predicate<String> getValuesFilter() {
        return valuesFilter;
    }

    @Override
    public void setAgentsFilter(Predicate<String> agentsFilter) {
        this.agentsFilter = agentsFilter;
    }

    @Override
    public Predicate<String> getAgentsFilter() {
        return agentsFilter;
    }

    @Override
    public AgentPlotChart getChart() {
        return chart;
    }

    @Override
    public void setChart(AgentPlotChart chart) {
        this.chart = chart;
    }
}
