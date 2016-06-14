package fr.irit.smac.demoamasfactory.infrastructure.impl;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasfactory.impl.BasicInfrastructure;
import fr.irit.smac.demoamasfactory.agent.IAgent;
import fr.irit.smac.demoamasfactory.infrastructure.IDemoInfrastructure;
import fr.irit.smac.demoamasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.service.plot.IPlotService;

public class DemoInfrastructure extends BasicInfrastructure<IAgent, IMessage>
    implements IDemoInfrastructure {

    @JsonProperty
    private IPlotService<IMessage> plotService;

    public DemoInfrastructure() {
        super();
    }

    private void initPlotService() {
        this.plotService.start();
        Map<String, IAgent> agentMap = this.getAgentHandler().getAgentMap();
        agentMap.forEach((s, a) -> a.setMonitor(this.plotService.createAgentMonitor(s)));
        this.plotService.setAgentsFilter(s -> "R 5_5|5_6".equals(s) || "5_5".equals(s) || "1_1".equals(s)
            || "10_10".equals(s) || "gen 20V".equals(s) || "gen 2".equals(s), agentMap);
    }

    public void init() {
        this.start();
        this.initPlotService();
    }
}
