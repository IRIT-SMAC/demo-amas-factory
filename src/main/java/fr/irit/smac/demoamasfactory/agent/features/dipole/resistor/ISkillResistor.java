package fr.irit.smac.demoamasfactory.agent.features.dipole.resistor;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.demoamasfactory.agent.features.dipole.ISkillDipole;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;

public interface ISkillResistor<K extends IKnowledgeResistor> extends ISkillDipole<K> {

    public void publishValues(ISkillPlot<IKnowledgePlot> skillPlot, String id);

    public void communicateIntensity(IKnowledgeSocial knowledgeSocial, String id);

    public void requestPotentialUpdate(IKnowledgeSocial knowledgeSocial, String id);

    public void handleIntensityDirectionRequest();
}
