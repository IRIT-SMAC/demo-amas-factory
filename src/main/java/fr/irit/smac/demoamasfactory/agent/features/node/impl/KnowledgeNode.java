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
package fr.irit.smac.demoamasfactory.agent.features.node.impl;

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasfactory.agent.impl.Knowledge;
import fr.irit.smac.demoamasfactory.agent.features.node.IKnowledgeNode;
import fr.irit.smac.libs.tooling.avt.AVTBuilder;
import fr.irit.smac.libs.tooling.avt.EFeedback;
import fr.irit.smac.libs.tooling.avt.IAVT;

public class KnowledgeNode extends Knowledge implements IKnowledgeNode {

    private Double worstPotentialCriticality = 0d;

    private EFeedback potentialDirection;

    private IAVT potential;

    private Map<String, Double> intensities = new HashMap<>();

    private Double previousISumChange = 0d;

    private boolean receivedPdr;

    public KnowledgeNode() {
        super();
        potential = new AVTBuilder().deltaMin(.001).deltaMax(100).build();
    }

    @Override
    public Double getPreviousISumChange() {
        return previousISumChange;
    }

    @Override
    public void setPreviousISumChange(Double previousISumChange) {
        this.previousISumChange = previousISumChange;
    }

    @Override
    public boolean isReceivedPdr() {
        return receivedPdr;
    }

    @Override
    public void setReceivedPdr(boolean receivedPdr) {
        this.receivedPdr = receivedPdr;
    }

    @Override
    public IAVT getPotential() {
        return potential;
    }

    @Override
    public Map<String, Double> getIntensities() {
        return intensities;
    }

    @Override
    public EFeedback getPotentialDirection() {
        return potentialDirection;
    }

    @Override
    public Double getWorstPotentialCriticality() {
        return worstPotentialCriticality;
    }

    @Override
    public void setWorstPotentialCriticality(Double worstPotentialCriticality) {
        this.worstPotentialCriticality = worstPotentialCriticality;
    }

    @Override
    public void setPotentialDirection(EFeedback potentialDirection) {
        this.potentialDirection = potentialDirection;
    }

    @Override
    public void setPotential(IAVT potential) {
        this.potential = potential;
    }
}
