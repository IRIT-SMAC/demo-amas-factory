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
package fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.impl;

import fr.irit.smac.demoamasfactory.agent.features.dipole.impl.KnowledgeDipole;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.IKnowledgeResistor;
import fr.irit.smac.libs.tooling.avt.EFeedback;

public class KnowledgeResistor extends KnowledgeDipole implements IKnowledgeResistor {

    private Double worstIntensityCriticality = 0d;

    private EFeedback intensityDirection;

    public KnowledgeResistor() {
        super();
    }
    
    public KnowledgeResistor(double resistor) {
        this.resistor = resistor;
    }

    @Override
    public Double getWorstIntensityCriticality() {
        return worstIntensityCriticality;
    }

    @Override
    public void setWorstIntensityCriticality(Double worstIntensityCriticality) {
        this.worstIntensityCriticality = worstIntensityCriticality;
    }

    @Override
    public void setIntensityDirection(EFeedback intensityDirection) {
        this.intensityDirection = intensityDirection;
    }

    @Override
    public EFeedback getIntensityDirection() {
        return intensityDirection;
    }

}
