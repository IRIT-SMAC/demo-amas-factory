package fr.irit.smac.demoamasfactory.agent.features;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasfactory.agent.features.IFeature;
import fr.irit.smac.amasfactory.agent.features.impl.Features;
import fr.irit.smac.demoamasfactory.agent.features.dipole.KnowledgeDipole;
import fr.irit.smac.demoamasfactory.agent.features.dipole.KnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.KnowledgeUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.dipole.SkillDipole;
import fr.irit.smac.demoamasfactory.agent.features.dipole.SkillResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.SkillUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.node.KnowledgeNode;
import fr.irit.smac.demoamasfactory.agent.features.node.SkillNode;
import fr.irit.smac.demoamasfactory.agent.features.plot.KnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.SkillPlot;

public class MyFeatures extends Features implements IMyFeatures {

    @JsonProperty
    public IFeature<KnowledgeNode, SkillNode<KnowledgeNode>> featureNode;
    
    @JsonProperty
    public IFeature<KnowledgeDipole, SkillDipole<KnowledgeDipole>> featureDipole;

    @JsonProperty
    public IFeature<KnowledgeResistor, SkillResistor<KnowledgeResistor>> featureResistor;

    @JsonProperty
    public IFeature<KnowledgePlot, SkillPlot<KnowledgePlot>> featurePlot;

    @JsonProperty
    public IFeature<KnowledgeUGenerator, SkillUGenerator<KnowledgeUGenerator>> featureUGenerator;

    @Override
    public IFeature<KnowledgeDipole, SkillDipole<KnowledgeDipole>> getFeatureDipole() {
        return this.featureDipole;
    }

    @Override
    public IFeature<KnowledgeResistor, SkillResistor<KnowledgeResistor>> getFeatureResistor() {
        return this.featureResistor;
    }

    @Override
    public IFeature<KnowledgeUGenerator, SkillUGenerator<KnowledgeUGenerator>> getFeatureUGenerator() {
        return this.featureUGenerator;
    }

    @Override
    public IFeature<KnowledgePlot, SkillPlot<KnowledgePlot>> getFeaturePlot() {
        return this.featurePlot;
    }

    @Override
    public IFeature<KnowledgeNode, SkillNode<KnowledgeNode>> getFeatureNode() {
        return this.featureNode;
    }

}
