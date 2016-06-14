package fr.irit.smac.demoamasfactory.service.plot;

import java.util.Map;
import java.util.function.Predicate;

import fr.irit.smac.amasfactory.agent.IInfrastructureAgent;
import fr.irit.smac.amasfactory.service.IInfraService;
import fr.irit.smac.demoamasfactory.agent.IAgent;

public interface IPlotService<M> extends IInfraService<IInfrastructureAgent<M>, M>{

    public IAgentMonitor createAgentMonitor(String id);
    
    public void setAgentsFilter(Predicate<String> agentsFilter, Map<String,IAgent> agentMap);

}
