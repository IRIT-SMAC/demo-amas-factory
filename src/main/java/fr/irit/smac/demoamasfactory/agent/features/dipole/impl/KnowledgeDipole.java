package fr.irit.smac.demoamasfactory.agent.features.dipole.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasfactory.agent.impl.Knowledge;
import fr.irit.smac.demoamasfactory.agent.features.dipole.IKnowledgeDipole;

public class KnowledgeDipole extends Knowledge implements IKnowledgeDipole {

    @JsonProperty
    public Double tension;
    
    public Double firstPotential;
    public Double secondPotential;
    
    @JsonProperty
    public Double resistor;
    public Double intensity;

    public KnowledgeDipole() {
        super();
    }

    @Override
    public Double getU() {
        return tension;
    }

    @Override
    public Double getR() {
        return resistor;
    }

    @Override
    public Double getI() {
        return intensity;
    }

    @Override
    public Double getFirstPotential() {
        return firstPotential;
    }

    @Override
    public Double getSecondPotential() {
        return secondPotential;
    }

    @Override
    public void setFirstPotential(Double firstPotential) {
        this.firstPotential = firstPotential;
        this.computeUfromV();
    }

    @Override
    public void setSecondPotential(Double secondPotential) {
        this.secondPotential = secondPotential;
        this.computeUfromV();
    }

    @Override
    public void computeUfromV() {
        if (firstPotential != null && secondPotential != null) {
            Double old = tension;
            tension = secondPotential - firstPotential;
            if (!tension.equals(old)) {
                computeIfromU();
            }
        }
    }

    @Override
    public void computeIfromU() {
        if (tension != null && resistor != null && !resistor.equals(0d)) {
            intensity = tension / resistor;
        }
    }

    @Override
    public String toString() {
        return "Dipole";
        // + firstTerminal + ": " + firstPotential
        // + "V " + secondTerminal + ": " + secondPotential + "V U="
        // + tension + "V R=" + resistor + "Ohm I=" + intensity + "A.";
    }
}