package fr.irit.smac.demoamasfactory.agent.features.dipole;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasfactory.agent.impl.Knowledge;
import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge;

public class KnowledgeDipole extends Knowledge implements IDipoleKnowledge {

    private static final long serialVersionUID = 1L;

    public Double firstPotential;
    public Double secondPotential;
    @JsonProperty
    public Double tension;
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

    public Double getFirstPotential() {
        return firstPotential;
    }

    public Double getSecondPotential() {
        return secondPotential;
    }

    public void setFirstPotential(Double firstPotential) {
        this.firstPotential = firstPotential;
    }

    public void setSecondPotential(Double secondPotential) {
        this.secondPotential = secondPotential;
    }

    protected void computeUfromV() {
        if (firstPotential != null && secondPotential != null) {
            Double old = tension;
            tension = secondPotential - firstPotential;
            if (!tension.equals(old)) {
                computeIfromU();
            }
        }
    }

    protected void computeIfromU() {
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