package fr.irit.smac.demoamasfactory.service;

import fr.irit.smac.amasfactory.service.IServices;
import fr.irit.smac.demoamasfactory.service.plot.IPlotService;

public interface IMyServices extends IServices{

    public IPlotService getPlotService();

}
