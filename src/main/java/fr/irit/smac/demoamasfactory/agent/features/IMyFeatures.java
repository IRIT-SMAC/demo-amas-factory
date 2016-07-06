package fr.irit.smac.demoamasfactory.agent.features;

import fr.irit.smac.amasfactory.agent.features.IFeature;
import fr.irit.smac.demoamasfactory.agent.features.plot.KnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.SkillPlot;

public interface IMyFeatures {

    public IFeature<KnowledgePlot, SkillPlot<KnowledgePlot>> getFeaturePlot();

}
