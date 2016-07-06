package fr.irit.smac.demoamasfactory.agent.features.plot;

import java.util.function.Predicate;

import fr.irit.smac.amasfactory.agent.IKnowledge;
import fr.irit.smac.libs.tooling.plot.server.AgentPlotChart;

public interface IKnowledgePlot extends IKnowledge{

    public Predicate<String> getValuesFilter();

    public void setAgentsFilter(Predicate<String> agentsFilter);

    public Predicate<String> getAgentsFilter();

    public AgentPlotChart getChart();

    public void setChart(AgentPlotChart chart);

}
