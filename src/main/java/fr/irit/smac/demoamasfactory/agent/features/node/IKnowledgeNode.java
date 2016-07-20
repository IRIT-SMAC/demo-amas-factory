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
package fr.irit.smac.demoamasfactory.agent.features.node;

import java.util.Map;

import fr.irit.smac.amasfactory.agent.IKnowledge;
import fr.irit.smac.libs.tooling.avt.EFeedback;
import fr.irit.smac.libs.tooling.avt.IAVT;

public interface IKnowledgeNode extends IKnowledge {

    public enum ENode {

        PORT("port"), POTENTIAL_DIRECTION("potentialDirection"), INTENSITY("intensity");

        String name;

        private ENode(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public Double getPreviousISumChange();

    public void setPreviousISumChange(Double previousISumChange);

    public boolean isReceivedPdr();

    public void setReceivedPdr(boolean receivedPdr);

    public IAVT getPotential();

    public Map<String, Double> getIntensities();

    public EFeedback getPotentialDirection();

    public Double getWorstPotentialCriticality();

    public void setWorstPotentialCriticality(Double worstPotentialCriticality);

    public void setPotentialDirection(EFeedback potentialDirection);

    public void setPotential(IAVT potential);

}
