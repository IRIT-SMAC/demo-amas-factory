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
package fr.irit.smac.demoamasfactory.agent.features.dipole;

import fr.irit.smac.amasfactory.agent.IKnowledge;

public interface IKnowledgeDipole extends IKnowledge {

    public enum ETerminal {

        FIRST("firstTerminal"), SECOND("secondTerminal");

        String name;

        private ETerminal(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    /**
     * @return the tension
     */
    public Double getU();

    /**
     * @return the resistor
     */
    public Double getR();

    /**
     * @return the intensity
     */
    public Double getI();

    public Double getFirstPotential();

    public Double getSecondPotential();

    public void setFirstPotential(Double firstPotential);

    public void setSecondPotential(Double secondPotential);

    public void computeUfromV();

    public void computeIfromU();

}