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
package fr.irit.smac.demoamasfactory.agent.features.dipole.generator.impl;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.features.social.ISkillSocial;
import fr.irit.smac.demoamasfactory.agent.features.dipole.IKnowledgeDipole.ETerminal;
import fr.irit.smac.demoamasfactory.agent.features.dipole.generator.IKnowledgeUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.dipole.generator.ISkillUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.dipole.impl.SkillDipole;
import fr.irit.smac.demoamasfactory.agent.features.node.impl.PotentialDirection;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;
import fr.irit.smac.libs.tooling.avt.EFeedback;

public class SkillUGenerator<K extends IKnowledgeUGenerator> extends SkillDipole<K>implements ISkillUGenerator<K> {

    @Override
    public void publishValues(ISkillPlot<IKnowledgePlot> skillPlot, String id) {

        skillPlot.publish("tension", knowledge.getU(), id);
        if (knowledge.getFirstPotential() != null) {
            skillPlot.publish("firstPotential",
                knowledge.getFirstPotential(), id);
        }
        if (knowledge.getSecondPotential() != null) {
            skillPlot.publish("secondPotential",
                knowledge.getSecondPotential(), id);
        }
    }

    @Override
    public void compareVoltageWithGeneratorTension(ISkillSocial<IKnowledgeSocial> skillSocial, String id) {

        // compare actual voltage with generator tension
        Double actualVoltage = knowledge.getSecondPotential()
            - knowledge.getFirstPotential();
        Double error = knowledge.getU() - actualVoltage;

        if (error > 0) {
            // ask to increase the actualVoltage
            requestUChange(knowledge.getFirstPotential(), knowledge.getSecondPotential(), id,
                ETerminal.FIRST.getName(),
                ETerminal.SECOND.getName(), skillSocial);
        }
        else if (error < 0) {
            // ask to decrease the actualVoltage
            requestUChange(knowledge.getSecondPotential(), knowledge.getFirstPotential(), id,
                ETerminal.SECOND.getName(),
                ETerminal.FIRST.getName(), skillSocial);
        }
    }

    private void requestUChange(Double lowerReceiver, Double greaterReceiver, String id, String idLowerReceiver,
        String idGreaterReceiver, ISkillSocial<IKnowledgeSocial> skillSocial) {

        PotentialDirection greaterMsg = new PotentialDirection(
            EFeedback.GREATER, 100d, greaterReceiver, id);
        PotentialDirection lowerMsg = new PotentialDirection(
            EFeedback.LOWER, 100d, lowerReceiver, id);

        skillSocial.sendDataToPortTarget(idGreaterReceiver + "PotentialDirection", greaterMsg, id);
        skillSocial.sendDataToPortTarget(idLowerReceiver + "PotentialDirection", lowerMsg, id);

        logger.debug("Sent msg: UP: " + idGreaterReceiver + " DOWN: " + idLowerReceiver);
    }
}
