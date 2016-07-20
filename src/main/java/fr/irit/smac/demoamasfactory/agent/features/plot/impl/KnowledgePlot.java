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
package fr.irit.smac.demoamasfactory.agent.features.plot.impl;

import java.util.function.Predicate;

import fr.irit.smac.amasfactory.agent.impl.Knowledge;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.libs.tooling.plot.server.AgentPlotChart;

public class KnowledgePlot extends Knowledge implements IKnowledgePlot {

    private String            agentId;
    private AgentPlotChart    chart;
    private Predicate<String> agentsFilter = s -> true;
    private Predicate<String> valuesFilter = s -> true;

    public KnowledgePlot() {
        super();
    }

    @Override
    public Predicate<String> getValuesFilter() {
        return valuesFilter;
    }

    @Override
    public void setAgentsFilter(Predicate<String> agentsFilter) {
        this.agentsFilter = agentsFilter;
    }

    @Override
    public Predicate<String> getAgentsFilter() {
        return agentsFilter;
    }

    @Override
    public AgentPlotChart getChart() {
        return chart;
    }

    @Override
    public void setChart(AgentPlotChart chart) {
        this.chart = chart;
    }

    @Override
    public String getAgentId() {
        return agentId;
    }

}
