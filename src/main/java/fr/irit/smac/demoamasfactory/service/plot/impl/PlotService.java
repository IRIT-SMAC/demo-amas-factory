package fr.irit.smac.demoamasfactory.service.plot.impl;

import java.util.function.Predicate;

import fr.irit.smac.amasfactory.impl.ShutdownRuntimeException;
import fr.irit.smac.demoamasfactory.service.plot.IPlotService;
import fr.irit.smac.libs.tooling.plot.server.AgentPlotChart;

public class PlotService implements IPlotService {

    Predicate<String> agentsFilter = s -> true;
    Predicate<String> valuesFilter = s -> true;

    AgentPlotChart nbAgent = new AgentPlotChart("# Agents");
    
    @Override
    public void start() {
        nbAgent.add(0);
    }

    @Override
    public void shutdown() throws ShutdownRuntimeException {
        // Nothing to do
    }
    
    public void initMainChart(double nbAgents) {
        this.nbAgent.add(nbAgents);
    }

    @Override
    public void setAgentsFilter(Predicate<String> agentsFilter) {
        this.agentsFilter = agentsFilter;
    }

    @Override
    public Predicate<String> getAgentsFilter() {
        return agentsFilter;
    }
}
