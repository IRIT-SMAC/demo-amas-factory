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
