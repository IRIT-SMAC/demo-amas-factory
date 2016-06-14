package fr.irit.smac.demoamasfactory.agent;

import fr.irit.smac.amasfactory.agent.IInfrastructureAgent;
import fr.irit.smac.demoamasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.service.plot.IAgentMonitor;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public interface IAgent extends IInfrastructureAgent<IMessage>, ITwoStepsAgent{

    public void setMonitor(IAgentMonitor monitor);

    public IAgentMonitor getMonitor();
}
