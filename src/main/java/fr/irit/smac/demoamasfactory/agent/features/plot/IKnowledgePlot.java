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
package fr.irit.smac.demoamasfactory.agent.features.plot;

import java.util.function.Predicate;

import fr.irit.smac.amasfactory.agent.IKnowledge;
import fr.irit.smac.libs.tooling.plot.server.AgentPlotChart;

public interface IKnowledgePlot extends IKnowledge{

    public Predicate<String> getValuesFilter();

    public void setAgentsFilter(Predicate<String> agentsFilter);

    public Predicate<String> getAgentsFilter();

    public AgentPlotChart getChart();

    public void setChart(AgentPlotChart chart);

    public String getAgentId();

}
