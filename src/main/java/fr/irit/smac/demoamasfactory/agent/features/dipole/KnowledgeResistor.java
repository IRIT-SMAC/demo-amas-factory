package fr.irit.smac.demoamasfactory.agent.features.dipole;

import java.util.Collection;

import fr.irit.smac.demoamasfactory.message.impl.IntensityDirectionRequest;
import fr.irit.smac.libs.tooling.avt.EFeedback;

public class KnowledgeResistor extends KnowledgeDipole {

    private static final long serialVersionUID = 1L;

    private Double worstIntensityCriticality = 0d;

    private EFeedback intensityDirection;

    private Collection<IntensityDirectionRequest> intensityDirectionRequest;

    public KnowledgeResistor() {
    }

    public Double getWorstIntensityCriticality() {
        return worstIntensityCriticality;
    }

    public void setWorstIntensityCriticality(Double worstIntensityCriticality) {
        this.worstIntensityCriticality = worstIntensityCriticality;
    }

    public void setIntensityDirection(EFeedback intensityDirection) {
        this.intensityDirection = intensityDirection;
    }

    public EFeedback getIntensityDirection() {
        return intensityDirection;
    }

    public Collection<IntensityDirectionRequest> getIntensityDirectionRequest() {
        return intensityDirectionRequest;
    }
}
