package fr.irit.smac.demoamasfactory.agent.features.node.impl;

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasfactory.agent.impl.Knowledge;
import fr.irit.smac.demoamasfactory.agent.features.node.IKnowledgeNode;
import fr.irit.smac.libs.tooling.avt.AVTBuilder;
import fr.irit.smac.libs.tooling.avt.EFeedback;
import fr.irit.smac.libs.tooling.avt.IAVT;

public class KnowledgeNode extends Knowledge implements IKnowledgeNode {

    public Double worstPotentialCriticality = 0d;

    public EFeedback potentialDirection;

    public IAVT potential;

    public Map<String, Double> intensities = new HashMap<String, Double>();

    private Double previousISumChange = 0d;

    private boolean receivedPdr;

    public KnowledgeNode() {
        super();
        this.potential = new AVTBuilder().deltaMin(.001).deltaMax(100).build();
    }

    @Override
    public Double getPreviousISumChange() {
        return previousISumChange;
    }

    @Override
    public void setPreviousISumChange(Double previousISumChange) {
        this.previousISumChange = previousISumChange;
    }

    @Override
    public boolean isReceivedPdr() {
        return receivedPdr;
    }

    @Override
    public void setReceivedPdr(boolean receivedPdr) {
        this.receivedPdr = receivedPdr;
    }

    @Override
    public IAVT getPotential() {
        return potential;
    }

    @Override
    public Map<String, Double> getIntensities() {
        return intensities;
    }

    @Override
    public EFeedback getPotentialDirection() {
        return potentialDirection;
    }

    @Override
    public Double getWorstPotentialCriticality() {
        return worstPotentialCriticality;
    }

    @Override
    public void setWorstPotentialCriticality(Double worstPotentialCriticality) {
        this.worstPotentialCriticality = worstPotentialCriticality;
    }

    @Override
    public void setPotentialDirection(EFeedback potentialDirection) {
        this.potentialDirection = potentialDirection;
    }

    @Override
    public void setPotential(IAVT potential) {
        this.potential = potential;
    }
}
