package fr.irit.smac.demoamasfactory.message.impl;

/**
 * A message to send a value
 *
 * @param <T>
 *            type of the value
 */
public class ValueMessage<T> extends Message {

	T value;

	public ValueMessage(String sender, T value) {
		super(sender);
		this.value = value;
	}

	public T getValue() {
		return value;
	}
}
