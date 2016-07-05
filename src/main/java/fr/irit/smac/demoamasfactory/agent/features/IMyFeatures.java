package fr.irit.smac.demoamasfactory.agent.features;

import fr.irit.smac.amasfactory.agent.features.IFeature;
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

public interface IMyFeatures {

    public IFeature<KnowledgeDipole, SkillDipole<KnowledgeDipole>> getFeatureDipole();

    public IFeature<KnowledgeResistor, SkillResistor<KnowledgeResistor>> getFeatureResistor();

    public IFeature<KnowledgeUGenerator, SkillUGenerator<KnowledgeUGenerator>> getFeatureUGenerator();

    public IFeature<KnowledgePlot, SkillPlot<KnowledgePlot>> getFeaturePlot();
    
    public IFeature<KnowledgeNode, SkillNode<KnowledgeNode>> getFeatureNode();

}
