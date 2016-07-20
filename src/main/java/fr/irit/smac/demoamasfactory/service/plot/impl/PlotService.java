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
package fr.irit.smac.demoamasfactory.service.plot.impl;

import java.util.function.Predicate;

import fr.irit.smac.amasfactory.impl.ShutdownRuntimeException;
import fr.irit.smac.demoamasfactory.service.plot.IPlotService;
import fr.irit.smac.libs.tooling.plot.server.AgentPlotChart;

public class PlotService implements IPlotService {

    Predicate<String> agentsFilter = s -> true;
    Predicate<String> valuesFilter = s -> true;

    AgentPlotChart nbAgent = new AgentPlotChart("# Agents");
    
    @Override
    public void start() {
        nbAgent.add(0);
    }

    @Override
    public void shutdown() throws ShutdownRuntimeException {
        // Nothing to do
    }

    @Override
    public void initMainChart(double nbAgents) {
        nbAgent.add(nbAgents);
    }

    @Override
    public void setAgentsFilter(Predicate<String> agentsFilter) {
        this.agentsFilter = agentsFilter;
    }

    @Override
    public Predicate<String> getAgentsFilter() {
        return agentsFilter;
    }
}
