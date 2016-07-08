package fr.irit.smac.demoamasfactory.service;

import fr.irit.smac.amasfactory.service.IServices;
import fr.irit.smac.demoamasfactory.service.plot.IPlotService;

public interface IMyServices<A> extends IServices<A>{

    public IPlotService getPlotService();

}
