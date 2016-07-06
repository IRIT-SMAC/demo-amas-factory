package fr.irit.smac.demoamasfactory.agent.features.dipole.generator;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.demoamasfactory.agent.features.dipole.ISkillDipole;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;

public interface ISkillUGenerator<K extends IKnowledgeUGenerator> extends ISkillDipole<K> {

    public void publishValues(ISkillPlot<IKnowledgePlot> skillPlot, String id);

    public void compareVoltageWithGeneratorTension(IKnowledgeSocial knowledgeSocial, String id);

}
