package fr.irit.smac.demoamasfactory.message.impl;

import fr.irit.smac.libs.tooling.avt.EFeedback;

public class PotentialDirectionRequest extends DirectionRequest<Double> {

	public PotentialDirectionRequest(String sender, EFeedback direction,
			Double criticality, Double knownValue) {
		super(sender, direction, criticality, knownValue);
	}

}
