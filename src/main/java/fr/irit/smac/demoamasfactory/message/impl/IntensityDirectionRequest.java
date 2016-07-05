package fr.irit.smac.demoamasfactory.message.impl;

import fr.irit.smac.libs.tooling.avt.EFeedback;

public class IntensityDirectionRequest extends DirectionRequest<Double> {

	public IntensityDirectionRequest(String sender, EFeedback direction,
			Double criticality, Double knownValue) {
		super(EMyMessageType.INTENSITY_DIRECTION_REQUEST,sender, direction, criticality, knownValue);
	}

}
