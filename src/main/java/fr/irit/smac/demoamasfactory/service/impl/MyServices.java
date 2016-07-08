package fr.irit.smac.demoamasfactory.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasfactory.agent.IAgent;
import fr.irit.smac.amasfactory.agent.IKnowledge;
import fr.irit.smac.amasfactory.agent.ISkill;
import fr.irit.smac.amasfactory.agent.features.IFeature;
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

public class MyServices<A extends IAgent<F, K, S, P>, F extends IMyFeatures, K extends IKnowledge, S extends ISkill<K>, P extends IFeature<K, S>>
    extends Services<A>implements IMyServices<A> {

    @JsonProperty
    private IPlotService plotService;

    public MyServices(
        @JsonProperty(value = "messagingService", required = true) IMessagingService<IMessage> messagingService,
        @JsonProperty(value = "agentHandlerService", required = true) IAgentHandlerService<A> agentHandlerService,
        @JsonProperty(value = "executionService", required = true) IExecutionService<A> executionService,
        @JsonProperty(value = "loggingService", required = true) ILoggingService<A> loggingService,
        @JsonProperty(value = "hazelcastService", required = true) IDataSharingService<A> hazelcastService,
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
        this.getAgentHandlerService().getAgentMap()
            .forEach((k, v) -> v.getFeatures().getFeaturePlot().getKnowledge().setChart(this.plotService.initChart(k)));

    }
}
