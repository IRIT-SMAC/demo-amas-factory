package fr.irit.smac.demoamasfactory.message.impl;

public class PotentialMsg extends ValueMessage<Double> {

	public PotentialMsg(String sender, Double potential) {
		super(EMyMessageType.POTENTIAL, sender, potential);
	}

}
