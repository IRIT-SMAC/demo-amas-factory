package fr.irit.smac.demoamasfactory.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.amasfactory.service.agenthandler.IAgentHandlerService;
import fr.irit.smac.amasfactory.service.datasharing.IDataSharingService;
import fr.irit.smac.amasfactory.service.execution.IExecutionService;
import fr.irit.smac.amasfactory.service.impl.Services;
import fr.irit.smac.amasfactory.service.logging.ILoggingService;
import fr.irit.smac.amasfactory.service.messaging.IMessagingService;
import fr.irit.smac.demoamasfactory.agent.features.IMyFeatures;
import fr.irit.smac.demoamasfactory.service.IMyServices;
import fr.irit.smac.demoamasfactory.service.plot.IPlotService;

public class MyServices extends Services implements IMyServices {

    @JsonProperty
    private IPlotService plotService;

    public MyServices(
        @JsonProperty(value = "messagingService", required = true) IMessagingService<IMessage> messagingService,
        @JsonProperty(value = "agentHandlerService", required = true) IAgentHandlerService agentHandlerService,
        @JsonProperty(value = "executionService", required = true) IExecutionService executionService,
        @JsonProperty(value = "loggingService", required = true) ILoggingService loggingService,
        @JsonProperty(value = "hazelcastService", required = true) IDataSharingService hazelcastService,
        @JsonProperty(value = "plotService", required = true) IPlotService plotService) {
        super(messagingService, agentHandlerService, executionService, loggingService, hazelcastService);

        this.plotService = plotService;
    }

    @Override
    public IPlotService getPlotService() {
        return plotService;
    }

    @Override
    public void start() {
        super.start();
        this.getAgentHandlerService().getAgentMap().forEach((k, v) -> ((IMyFeatures) v.getFeatures()).getFeaturePlot()
            .getKnowledge().setChart(this.plotService.initChart(k)));

    }
}
