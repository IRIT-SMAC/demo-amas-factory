package fr.irit.smac.demoamasfactory.knowledge.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasfactory.agent.impl.SimpleKnowledge;
import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge;

public class DipoleKnowledgeImpl extends SimpleKnowledge implements IDipoleKnowledge {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    protected String firstTerminal;
    @JsonProperty
    protected String secondTerminal;
    public Double    firstPotential;
    public Double    secondPotential;
    @JsonProperty
    public Double    tension;
    public Double    resistor;
    public Double    intensity;

    public DipoleKnowledgeImpl() {
        super();
    }

    @Override
    public String getId(Terminal terminal) {
        switch (terminal) {
            case FIRST:
                return firstTerminal;
            case SECOND:
                return secondTerminal;
        }
        return null;
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
    public Double getV(Terminal terminal) {
        switch (terminal) {
            case FIRST:
                return firstPotential;
            case SECOND:
                return secondPotential;
        }
        return null;
    }

    @Override
    public void setTerminalV(Terminal terminal, Double potential) {
        switch (terminal) {
            case FIRST:
                firstPotential = potential;
                break;
            case SECOND:
                secondPotential = potential;
                break;
        }
        computeUfromV();
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
        return "Dipole " + this.getId() + " " + firstTerminal + ": " + firstPotential
            + "V " + secondTerminal + ": " + secondPotential + "V U="
            + tension + "V R=" + resistor + "Ohm I=" + intensity + "A.";
    }

    @Override
    public Terminal getTerminal(String id) {
        if (id == null) {
            return null;
        }
        if (id.equals(firstTerminal)) {
            return Terminal.FIRST;
        }
        if (id.equals(secondTerminal)) {
            return Terminal.SECOND;
        }
        return null;
    }
}