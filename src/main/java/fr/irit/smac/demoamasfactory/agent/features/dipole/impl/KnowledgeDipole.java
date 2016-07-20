/*
 * #%L
 * demo-amas-factory
 * %%
 * Copyright (C) 2016 IRIT - SMAC Team and Brennus Analytics
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package fr.irit.smac.demoamasfactory.agent.features.dipole.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasfactory.agent.impl.Knowledge;
import fr.irit.smac.demoamasfactory.agent.features.dipole.IKnowledgeDipole;

public class KnowledgeDipole extends Knowledge implements IKnowledgeDipole {

    @JsonProperty
    protected Double tension;

    protected Double firstPotential;
    protected Double secondPotential;

    @JsonProperty
    protected Double resistor;
    protected Double intensity;

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
        computeUfromV();
    }

    @Override
    public void setSecondPotential(Double secondPotential) {
        this.secondPotential = secondPotential;
        computeUfromV();
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
    }
}