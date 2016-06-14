package fr.irit.smac.demoamasfactory.message.impl;

import fr.irit.smac.demoamasfactory.message.IMessage;

public class Message implements IMessage {

	protected String sender;

	public Message(String sender) {
		super();
		this.sender = sender;
	}

	/**
	 * @return the id of the message sender
	 */
	public String getSender() {
		return sender;
	}

}