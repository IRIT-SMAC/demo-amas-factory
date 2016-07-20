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
package fr.irit.smac.demoamasfactory.agent.features;

import fr.irit.smac.amasfactory.agent.features.ICommonFeatures;
import fr.irit.smac.amasfactory.agent.features.IFeature;
import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;

public interface IMyCommonFeatures extends ICommonFeatures{

    public IFeature<IKnowledgePlot, ISkillPlot<IKnowledgePlot>> getFeaturePlot();

    public void setFeaturePlot(Feature<IKnowledgePlot, ISkillPlot<IKnowledgePlot>> featurePlot);

}
