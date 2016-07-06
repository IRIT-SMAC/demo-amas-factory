package fr.irit.smac.demoamasfactory.agent.features.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import fr.irit.smac.amasfactory.agent.impl.Knowledge;
import fr.irit.smac.demoamasfactory.message.impl.IntensityMsg;
import fr.irit.smac.demoamasfactory.message.impl.PotentialDirectionRequest;
import fr.irit.smac.libs.tooling.avt.AVTBuilder;
import fr.irit.smac.libs.tooling.avt.EFeedback;
import fr.irit.smac.libs.tooling.avt.IAVT;

public class KnowledgeNode extends Knowledge {

    private static final long serialVersionUID = 1L;

    public Double worstPotentialCriticality = 0d;

    public EFeedback potentialDirection;

    public IAVT potential;

    public Collection<String> neighbors = new HashSet<String>();

    public Map<String, Double> intensities = new HashMap<String, Double>();

    private Double previousISumChange = 0d;

    private boolean receivedPdr;

    protected Collection<PotentialDirectionRequest> potentialDirectionRequestCollection;

    protected Collection<IntensityMsg> intensityMsgCollection;

    public KnowledgeNode() {
        super();
        this.potential = new AVTBuilder().deltaMin(.001).deltaMax(100).build();
        this.potentialDirectionRequestCollection = new ArrayList<>();
        this.intensityMsgCollection = new ArrayList<>();
    }

    public Double getPreviousISumChange() {
        return previousISumChange;
    }

    public void setPreviousISumChange(Double previousISumChange) {
        this.previousISumChange = previousISumChange;
    }

    public boolean isReceivedPdr() {
        return receivedPdr;
    }

    public void setReceivedPdr(boolean receivedPdr) {
        this.receivedPdr = receivedPdr;
    }

    public Collection<PotentialDirectionRequest> getPotentialDirectionRequest() {
        return this.potentialDirectionRequestCollection;
    }

    public Collection<IntensityMsg> getIntensityMsg() {
        return this.intensityMsgCollection;
    }
}
