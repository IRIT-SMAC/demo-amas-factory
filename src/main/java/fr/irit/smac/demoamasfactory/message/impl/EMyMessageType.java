package fr.irit.smac.demoamasfactory.message.impl;

import fr.irit.smac.amasfactory.message.IMessageType;

public enum EMyMessageType implements IMessageType {
    INTENSITY("intensity"), INTENSITY_DIRECTION_REQUEST("intensityDirectionRequest"), POTENTIAL_DIRECTION_REQUEST(
        "potentialDirectionRequest");

    private String name;

    private EMyMessageType(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
