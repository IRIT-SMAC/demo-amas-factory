package fr.irit.smac.demoamasfactory.agent.features.node;

import java.util.Map;

import fr.irit.smac.amasfactory.agent.IKnowledge;
import fr.irit.smac.libs.tooling.avt.EFeedback;
import fr.irit.smac.libs.tooling.avt.IAVT;

public interface IKnowledgeNode extends IKnowledge {

    public Double getPreviousISumChange();

    public void setPreviousISumChange(Double previousISumChange);

    public boolean isReceivedPdr();

    public void setReceivedPdr(boolean receivedPdr);

    public IAVT getPotential();

    public Map<String, Double> getIntensities();

    public EFeedback getPotentialDirection();

    public Double getWorstPotentialCriticality();

    public void setWorstPotentialCriticality(Double worstPotentialCriticality);

    public void setPotentialDirection(EFeedback potentialDirection);

    public void setPotential(IAVT potential);

}
