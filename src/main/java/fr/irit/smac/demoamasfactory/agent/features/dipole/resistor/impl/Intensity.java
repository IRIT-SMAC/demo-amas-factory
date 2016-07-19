package fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.impl;

public class Intensity {

    public Double value;
    public String sender;

    public Intensity(Double value, String sender) {
        this.value = value;
        this.sender = sender;
    }

    public Double getValue() {
        return this.value;
    }
    
    public String getSender() {
        return this.sender;
    }
}
