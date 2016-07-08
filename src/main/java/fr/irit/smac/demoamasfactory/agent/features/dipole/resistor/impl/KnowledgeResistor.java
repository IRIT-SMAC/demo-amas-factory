package fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.impl;

import fr.irit.smac.demoamasfactory.agent.features.dipole.impl.KnowledgeDipole;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.IKnowledgeResistor;
import fr.irit.smac.libs.tooling.avt.EFeedback;

public class KnowledgeResistor extends KnowledgeDipole implements IKnowledgeResistor {

    private static final long serialVersionUID = 1L;

    private Double worstIntensityCriticality = 0d;

    private EFeedback intensityDirection;

    public KnowledgeResistor() {
    }

    @Override
    public Double getWorstIntensityCriticality() {
        return worstIntensityCriticality;
    }

    @Override
    public void setWorstIntensityCriticality(Double worstIntensityCriticality) {
        this.worstIntensityCriticality = worstIntensityCriticality;
    }

    @Override
    public void setIntensityDirection(EFeedback intensityDirection) {
        this.intensityDirection = intensityDirection;
    }

    @Override
    public EFeedback getIntensityDirection() {
        return intensityDirection;
    }

}
