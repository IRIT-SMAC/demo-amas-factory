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

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.features.social.ISkillSocial;
import fr.irit.smac.demoamasfactory.agent.features.dipole.IKnowledgeDipole.ETerminal;
import fr.irit.smac.demoamasfactory.agent.features.dipole.impl.SkillDipole;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.IKnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.ISkillResistor;
import fr.irit.smac.demoamasfactory.agent.features.node.impl.PotentialDirection;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;
import fr.irit.smac.libs.tooling.avt.EFeedback;

public class SkillResistor<K extends IKnowledgeResistor> extends SkillDipole<K>implements ISkillResistor<K> {

    @Override
    public void publishValues(ISkillPlot<IKnowledgePlot> skillPlot, String id) {

        if (knowledge.getR() != null) {
            skillPlot.publish("resistor", knowledge.getR(), id);
        }
        if (knowledge.getI() != null) {
            skillPlot.publish("intensity", knowledge.getI(), id);
        }
        if (knowledge.getU() != null) {
            skillPlot.publish("tension", knowledge.getU(), id);
        }
        logger.debug(knowledge.toString());
    }

    @Override
    public void communicateIntensity(IKnowledgeSocial knowledgeSocial, ISkillSocial<IKnowledgeSocial> skillSocial,
        String id) {

        final Double intensity = knowledge.getI();

        if (intensity != null) {

            Intensity firstMsg = new Intensity(intensity, id);
            skillSocial.sendDataToPortTarget(ETerminal.FIRST.getName() + "Intensity", firstMsg, id);
            Intensity secondMsg = new Intensity(-intensity, id);
            skillSocial.sendDataToPortTarget(ETerminal.SECOND.getName() + "Intensity", secondMsg, id);
        }
    }

    @Override
    public void requestPotentialUpdate(IKnowledgeSocial knowledgeSocial, ISkillSocial<IKnowledgeSocial> skillSocial,
        String id) {

        EFeedback intensityDirection = knowledge.getIntensityDirection();
        Double worstIntensityCriticality = knowledge.getWorstIntensityCriticality();

        if (intensityDirection != null) {
            PotentialDirection firstMsg = new PotentialDirection(worstIntensityCriticality,
                knowledge.getFirstPotential(), id);
            firstMsg.setDirection(firstMsg.opposite(intensityDirection));

            skillSocial.sendDataToPortTarget(ETerminal.FIRST.getName() + "PotentialDirection", firstMsg, id);

            PotentialDirection secondMsg = new PotentialDirection(
                intensityDirection, worstIntensityCriticality, knowledge.getSecondPotential(), id);
            skillSocial.sendDataToPortTarget(ETerminal.SECOND.getName() + "PotentialDirection", secondMsg, id);

            knowledge.setWorstIntensityCriticality(0d);
            knowledge.setIntensityDirection(null);
        }
    }

}
