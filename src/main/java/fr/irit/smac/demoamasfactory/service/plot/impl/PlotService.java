package fr.irit.smac.demoamasfactory.service.plot.impl;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import fr.irit.smac.amasfactory.agent.IInfrastructureAgent;
import fr.irit.smac.amasfactory.impl.ShutdownRuntimeException;
import fr.irit.smac.amasfactory.service.impl.AbstractInfraService;
import fr.irit.smac.demoamasfactory.agent.IAgent;
import fr.irit.smac.demoamasfactory.service.plot.IAgentMonitor;
import fr.irit.smac.demoamasfactory.service.plot.IPlotService;
import fr.irit.smac.libs.tooling.plot.server.AgentPlotChart;

public class PlotService<M> extends AbstractInfraService<IInfrastructureAgent<M>, M>implements IPlotService<M> {

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

    @Override
    public IAgentMonitor createAgentMonitor(String id) {

        AgentMonitor agentMonitor = new AgentMonitor();
        agentMonitor.init(id, agentsFilter, valuesFilter);
        nbChart.add(nbAgents.incrementAndGet());
        return agentMonitor;
    }

    @Override
    public void setAgentsFilter(Predicate<String> agentsFilter, Map<String,IAgent> agentMap) {
        
        this.agentsFilter = agentsFilter;
        agentMap.forEach((s, agent) -> agent.getMonitor().setAgentsFilter(agentsFilter));
    }
}
