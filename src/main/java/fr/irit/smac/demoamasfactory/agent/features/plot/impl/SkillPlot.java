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

import fr.irit.smac.amasfactory.agent.impl.Skill;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;
import fr.irit.smac.libs.tooling.plot.server.AgentPlotChart;

public class SkillPlot<K extends IKnowledgePlot> extends Skill<K>implements ISkillPlot<K> {

    @Override
    public void publish(String name, Double value, String agentId) {

        if (knowledge.getAgentsFilter().test(agentId) && knowledge.getValuesFilter().test(name)) {
            
            if (knowledge.getChart() == null) {
                knowledge.setChart(new AgentPlotChart(agentId));
            }
            knowledge.getChart().add(name, value);
        }
    }
}
