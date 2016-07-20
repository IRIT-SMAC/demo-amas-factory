package fr.irit.smac.demoamasfactory.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasfactory.agent.IAgent;
import fr.irit.smac.amasfactory.agent.IKnowledge;
import fr.irit.smac.amasfactory.agent.ISkill;
import fr.irit.smac.amasfactory.service.impl.Services;
import fr.irit.smac.demoamasfactory.agent.features.IMyCommonFeatures;
import fr.irit.smac.demoamasfactory.service.IMyServices;
import fr.irit.smac.demoamasfactory.service.plot.IPlotService;

public class MyServices<A extends IAgent<F, IKnowledge, ISkill<IKnowledge>>, F extends IMyCommonFeatures>
    extends Services<A, F>implements IMyServices<A> {

    @JsonProperty
    private IPlotService plotService;

    public MyServices() {
        super();
    }

    @Override
    public IPlotService getPlotService() {
        return plotService;
    }

    @Override
    public void setPlotService(IPlotService plotService) {
        this.plotService = plotService;
    }

    @Override
    public void start() {
        super.start();

        plotService.start();
        plotService.initMainChart(getAgentHandlerService().getAgentMap().size());
        this.getAgentHandlerService().getAgentMap()
            .forEach((k, v) -> v.getFeatures().getFeaturePlot().getKnowledge()
                .setAgentsFilter(getPlotService().getAgentsFilter()));

    }
}
