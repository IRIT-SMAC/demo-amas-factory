package fr.irit.smac.demoamasfactory.service.plot.impl;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import fr.irit.smac.amasfactory.impl.ShutdownRuntimeException;
import fr.irit.smac.demoamasfactory.service.plot.IPlotService;
import fr.irit.smac.libs.tooling.plot.server.AgentPlotChart;

public class PlotService implements IPlotService {

    Predicate<String> agentsFilter = s -> true;
    Predicate<String> valuesFilter = s -> true;

    AtomicInteger  nbAgents = new AtomicInteger(0);
    AgentPlotChart nbChart  = new AgentPlotChart("# Agents");

    @Override
    public void start() {
        // TODO Auto-generated method stub

    }

    @Override
    public void shutdown() throws ShutdownRuntimeException {
        // TODO Auto-generated method stub

    }

    public AgentPlotChart getNbChart() {
        return nbChart;
    }

    @Override
    public AgentPlotChart initChart(String id) {

        AgentPlotChart chart = new AgentPlotChart(id);
        this.nbChart.add(this.nbAgents.incrementAndGet());
        return chart;
    }

    // @Override
    // public IAgentMonitor createAgentMonitor(String id) {
    //
    //// AgentMonitor agentMonitor = new AgentMonitor();
    //// agentMonitor.init(id, agentsFilter, valuesFilter);
    //// nbChart.add(nbAgents.incrementAndGet());
    //// return agentMonitor;
    // }

    // @Override
    // public void setAgentsFilter(Predicate<String> agentsFilter,
    // Map<String,IAgent> agentMap) {
    //
    // this.agentsFilter = agentsFilter;
    // agentMap.forEach((s, agent) ->
    // agent.getMonitor().setAgentsFilter(agentsFilter));
    // }
}
