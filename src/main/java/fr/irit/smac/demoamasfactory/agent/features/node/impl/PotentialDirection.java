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

import fr.irit.smac.libs.tooling.avt.EFeedback;

public class PotentialDirection {

    private EFeedback direction;
    private Double    criticality;
    private Double    knownValue;
    private String    sender;

    public PotentialDirection(EFeedback direction,
        Double criticality, Double knownValue, String sender) {
        this.direction = direction;
        this.criticality = criticality;
        this.knownValue = knownValue;
        this.sender = sender;
    }

    public PotentialDirection(Double criticality, Double knownValue, String sender) {
        this.criticality = criticality;
        this.knownValue = knownValue;
        this.sender = sender;
    }

    public EFeedback opposite(EFeedback direction) {
        switch (direction) {
            case GREATER:
                return EFeedback.LOWER;
            case LOWER:
                return EFeedback.GREATER;

            default:
                return EFeedback.GOOD;
        }
    }

    public EFeedback getDirection() {
        return direction;
    }

    public Double getCriticality() {
        return criticality;
    }

    public Double getKnownValue() {
        return knownValue;
    }

    public String getSender() {
        return sender;
    }

    public void setDirection(EFeedback direction) {
        this.direction = direction;
    }

    public void setCriticality(Double criticality) {
        this.criticality = criticality;
    }

    public void setKnownValue(Double knownValue) {
        this.knownValue = knownValue;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
