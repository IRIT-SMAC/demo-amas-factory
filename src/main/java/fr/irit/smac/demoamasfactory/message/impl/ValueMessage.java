package fr.irit.smac.demoamasfactory.message.impl;

import fr.irit.smac.amasfactory.message.Message;
import fr.irit.smac.amasfactory.message.IMessageType;

/**
 * A message to send a value
 *
 * @param <T>
 *            type of the value
 */
public class ValueMessage<T> extends Message {

	T value;

	public ValueMessage(IMessageType messageType, String sender, T value) {
		super(messageType, sender);
		this.value = value;
	}

	public T getValue() {
		return value;
	}
}
