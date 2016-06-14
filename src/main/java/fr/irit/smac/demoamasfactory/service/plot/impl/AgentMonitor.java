package fr.irit.smac.demoamasfactory.service.plot.impl;

import java.util.function.Predicate;

import fr.irit.smac.demoamasfactory.service.plot.IAgentMonitor;
import fr.irit.smac.libs.tooling.plot.server.AgentPlotChart;

public class AgentMonitor implements IAgentMonitor{

    String         agentId;
    AgentPlotChart chart;
    Predicate<String> agentsFilter = s -> true;
    Predicate<String> valuesFilter = s -> true;
    
    @Override
    public void init(String id, Predicate<String> agentsFilter, Predicate<String> valuesFilter) {
        this.agentId = id;
        this.agentsFilter = agentsFilter;
        this.valuesFilter = valuesFilter;
    }

    @Override
    public void setAgentsFilter(Predicate<String> agentsFilter) {
        this.agentsFilter = agentsFilter;
    }
    
    @Override
    public void publish(String name, Double value) {
        if (agentsFilter.test(agentId) && valuesFilter.test(name)) {
            if (chart == null) {
                chart = new AgentPlotChart(agentId);
            }
            chart.add(name, value);
        }
    }
}
