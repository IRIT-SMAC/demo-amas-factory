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
package fr.irit.smac.demoamasfactory.agent.impl;

import fr.irit.smac.amasfactory.agent.IKnowledge;
import fr.irit.smac.amasfactory.agent.ISkill;
import fr.irit.smac.amasfactory.agent.features.ICommonFeatures;
import fr.irit.smac.amasfactory.agent.impl.Agent;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

/**
 * This abstract class represents an agent with two steps. The agent node,
 * resistor and generator inherits this class but they could also just inherits
 * the class Agent and implements ITwoStepsAgent directly
 * 
 * @param <F>
 *            the features
 * @param <K>
 *            the knowledge
 * @param <S>
 *            the skill
 */
public abstract class TwoStepAgent<F extends ICommonFeatures, K extends IKnowledge, S extends ISkill<K>>
    extends Agent<F, K, S>
    implements ITwoStepsAgent {

}
