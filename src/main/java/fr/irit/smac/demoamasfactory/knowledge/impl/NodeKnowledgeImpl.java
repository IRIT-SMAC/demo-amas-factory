package fr.irit.smac.demoamasfactory.knowledge.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import fr.irit.smac.amasfactory.agent.impl.SimpleKnowledge;
import fr.irit.smac.libs.tooling.avt.AVTBuilder;
import fr.irit.smac.libs.tooling.avt.EFeedback;
import fr.irit.smac.libs.tooling.avt.IAVT;

public class NodeKnowledgeImpl extends SimpleKnowledge {

    private static final long serialVersionUID = 1L;

	public Double worstPotentialCriticality = 0d;
	public EFeedback potentialDirection;
	public IAVT potential;
	public Collection<String> neighbors = new HashSet<String>();
	public Map<String, Double> intensities = new HashMap<String, Double>();

	public NodeKnowledgeImpl() {
	    super();
	    this.potential = new AVTBuilder().deltaMin(.001).deltaMax(100).build();
	}
}
