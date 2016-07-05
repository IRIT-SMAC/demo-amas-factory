package fr.irit.smac.demoamasfactory.infrastructure.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasfactory.impl.BasicInfrastructure;
import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.amasfactory.service.agenthandler.IAgentHandlerService;
import fr.irit.smac.amasfactory.service.datasharing.IDataSharingService;
import fr.irit.smac.amasfactory.service.execution.IExecutionService;
import fr.irit.smac.amasfactory.service.logging.ILoggingService;
import fr.irit.smac.amasfactory.service.messaging.IMessagingService;
import fr.irit.smac.demoamasfactory.infrastructure.IDemoInfrastructure;
import fr.irit.smac.demoamasfactory.service.plot.IPlotService;

public class DemoInfrastructure extends BasicInfrastructure
    implements IDemoInfrastructure {

    @JsonProperty
    private IPlotService plotService;

    public DemoInfrastructure(
        @JsonProperty(value = "messagingService", required = true) IMessagingService<IMessage> messagingService,
        @JsonProperty(value = "agentHandlerService", required = true) IAgentHandlerService agentHandlerService,
        @JsonProperty(value = "executionService", required = true) IExecutionService executionService,
        @JsonProperty(value = "loggingService", required = true) ILoggingService loggingService,
        @JsonProperty(value = "hazelcastService", required = true) IDataSharingService hazelcastService,
        @JsonProperty(value = "plotService", required = true) IPlotService plotService) {
        super(messagingService,agentHandlerService,executionService,loggingService,hazelcastService);

        this.plotService = plotService;

//        this.start();
        this.initPlotService();

    }
    
    public DemoInfrastructure() {
        super();
    }

    private void initPlotService() {
        this.plotService.start();
//        Map<String, IAgent> agentMap = this.getAgentHandler().getAgentMap();
//        agentMap.forEach((s, a) -> a.setMonitor(this.plotService.createAgentMonitor(s)));
//        this.plotService.setAgentsFilter(s -> "R 5_5|5_6".equals(s) || "5_5".equals(s) || "1_1".equals(s)
//            || "10_10".equals(s) || "gen 20V".equals(s) || "gen 2".equals(s), agentMap);
    }

    public void init() {
//        this.start();
//        this.initPlotService();
    }
}
