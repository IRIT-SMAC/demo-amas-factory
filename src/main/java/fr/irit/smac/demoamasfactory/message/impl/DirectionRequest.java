package fr.irit.smac.demoamasfactory.message.impl;

import fr.irit.smac.amasfactory.message.Message;
import fr.irit.smac.amasfactory.message.IMessageType;
import fr.irit.smac.libs.tooling.avt.EFeedback;

/**
 * Message to communicate a wish about the change direction. It is useful to
 * tune a parameter and could be extended in case of multiple parameters.
 * 
 * 
 */
public class DirectionRequest<T> extends Message {

	public EFeedback direction;
	public Double criticality;
	public T knownValue;

	public DirectionRequest(IMessageType messageType, String sender, EFeedback direction,
			Double criticality, T knownValue) {
		super(messageType,sender);
		this.direction = direction;
		this.criticality = criticality;
		this.knownValue = knownValue;
	}

	public static EFeedback opposite(EFeedback direction) {
		switch (direction) {
		case GREATER:
			return EFeedback.LOWER;
		case LOWER:
			return EFeedback.GREATER;

		default:
			return EFeedback.GOOD;
		}
	}

	@Override
	public String toString() {
		return "sender: " + sender + " direction: " + direction
				+ "  criticality: " + criticality + "  knownvalue: "
				+ knownValue;
	}
}
