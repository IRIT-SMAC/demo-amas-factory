package fr.irit.smac.demoamasfactory.service.plot;

import fr.irit.smac.amasfactory.service.IInfraService;
import fr.irit.smac.libs.tooling.plot.server.AgentPlotChart;

public interface IPlotService extends IInfraService{

    public AgentPlotChart initChart(String id);

}
