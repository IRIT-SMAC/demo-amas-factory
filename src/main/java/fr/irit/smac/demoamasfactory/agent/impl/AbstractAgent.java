package fr.irit.smac.demoamasfactory.agent.impl;

import org.slf4j.Logger;

import fr.irit.smac.amasfactory.agent.impl.AbsInfrastructureAgent;
import fr.irit.smac.demoamasfactory.agent.IAgent;
import fr.irit.smac.demoamasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.service.plot.IAgentMonitor;
import fr.irit.smac.libs.tooling.messaging.IMsgBox;

public abstract class AbstractAgent extends AbsInfrastructureAgent<IMessage>implements IAgent {

    protected IMsgBox<IMessage> msgBox;
    protected Logger            logger;
    protected IAgentMonitor     monitor;

    @Override
    public void setMonitor(IAgentMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public IAgentMonitor getMonitor() {
        return this.monitor;
    }

    @Override
    protected void initParameters() {

        this.msgBox = this.getInfra().getMessagingService().getMsgBox(this.getId());
        this.logger = this.getInfra().getLoggingService().getAgentLogger(this.getId());
    }
}
