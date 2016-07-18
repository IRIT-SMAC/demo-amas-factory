package fr.irit.smac.demoamasfactory.agent.features.dipole.resistor;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.features.social.ISkillSocial;
import fr.irit.smac.demoamasfactory.agent.features.dipole.ISkillDipole;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;

public interface ISkillResistor<K> extends ISkillDipole<K> {

    public void publishValues(ISkillPlot<IKnowledgePlot> skillPlot, String id);

    public void communicateIntensity(IKnowledgeSocial knowledgeSocial, ISkillSocial<IKnowledgeSocial> skillSocial, String id);

    public void requestPotentialUpdate(IKnowledgeSocial knowledgeSocial, ISkillSocial<IKnowledgeSocial> skillSocial, String id);

}
