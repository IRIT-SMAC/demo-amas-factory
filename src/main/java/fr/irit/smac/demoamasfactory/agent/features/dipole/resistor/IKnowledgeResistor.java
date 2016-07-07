package fr.irit.smac.demoamasfactory.agent.features.dipole.resistor;

import java.util.Collection;

import fr.irit.smac.demoamasfactory.agent.features.dipole.IKnowledgeDipole;
import fr.irit.smac.demoamasfactory.message.impl.IntensityDirectionRequest;
import fr.irit.smac.libs.tooling.avt.EFeedback;

public interface IKnowledgeResistor extends IKnowledgeDipole {

    public Double getWorstIntensityCriticality();

    public void setWorstIntensityCriticality(Double worstIntensityCriticality);

    public void setIntensityDirection(EFeedback intensityDirection);

    public EFeedback getIntensityDirection();

    public Collection<IntensityDirectionRequest> getIntensityDirectionRequest();
}
