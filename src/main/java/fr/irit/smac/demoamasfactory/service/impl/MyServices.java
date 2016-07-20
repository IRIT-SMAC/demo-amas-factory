/*
 * #%L
 * demo-amas-factory
 * %%
 * Copyright (C) 2016 IRIT - SMAC Team and Brennus Analytics
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
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
        getAgentHandlerService().getAgentMap()
            .forEach((k, v) -> v.getFeatures().getFeaturePlot().getKnowledge()
                .setAgentsFilter(getPlotService().getAgentsFilter()));

    }
}
