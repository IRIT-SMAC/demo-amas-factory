package fr.irit.smac.demoamasfactory.service.plot;

import java.util.function.Predicate;

import fr.irit.smac.amasfactory.service.IService;

public interface IPlotService extends IService{

    public void setAgentsFilter(Predicate<String> agentsFilter);

    public Predicate<String> getAgentsFilter();

    public void initMainChart(double nbAgents);

}
