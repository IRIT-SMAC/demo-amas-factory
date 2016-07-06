package fr.irit.smac.demoamasfactory.agent.features;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasfactory.agent.features.IFeature;
import fr.irit.smac.amasfactory.agent.features.impl.Features;
import fr.irit.smac.demoamasfactory.agent.features.plot.KnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.SkillPlot;

public class MyFeatures extends Features implements IMyFeatures {

    @JsonProperty
    public IFeature<KnowledgePlot, SkillPlot<KnowledgePlot>> featurePlot;

    @Override
    public IFeature<KnowledgePlot, SkillPlot<KnowledgePlot>> getFeaturePlot() {
        return this.featurePlot;
    }

}
