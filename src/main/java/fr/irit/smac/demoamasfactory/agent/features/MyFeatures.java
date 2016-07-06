package fr.irit.smac.demoamasfactory.agent.features;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasfactory.agent.features.IFeature;
import fr.irit.smac.amasfactory.agent.features.impl.Features;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;

public class MyFeatures extends Features implements IMyFeatures {

    @JsonProperty
    public IFeature<IKnowledgePlot, ISkillPlot<IKnowledgePlot>> featurePlot;

    @Override
    public IFeature<IKnowledgePlot, ISkillPlot<IKnowledgePlot>> getFeaturePlot() {
        return this.featurePlot;
    }

}
