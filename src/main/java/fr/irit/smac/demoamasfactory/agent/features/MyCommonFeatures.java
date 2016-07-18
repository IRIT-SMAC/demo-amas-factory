package fr.irit.smac.demoamasfactory.agent.features;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasfactory.agent.features.IFeature;
import fr.irit.smac.amasfactory.agent.features.impl.CommonFeatures;
import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;

public class MyCommonFeatures extends CommonFeatures implements IMyCommonFeatures {

    @JsonProperty
    public IFeature<IKnowledgePlot, ISkillPlot<IKnowledgePlot>> featurePlot;

    @Override
    public IFeature<IKnowledgePlot, ISkillPlot<IKnowledgePlot>> getFeaturePlot() {
        return this.featurePlot;
    }

    @Override
    public void setFeaturePlot(Feature<IKnowledgePlot, ISkillPlot<IKnowledgePlot>> featurePlot) {
        this.featurePlot = featurePlot;
    }

}
