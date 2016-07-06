package fr.irit.smac.demoamasfactory.agent.features;

import fr.irit.smac.amasfactory.agent.features.IFeature;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;

public interface IMyFeatures {

    public IFeature<IKnowledgePlot, ISkillPlot<IKnowledgePlot>> getFeaturePlot();

}
