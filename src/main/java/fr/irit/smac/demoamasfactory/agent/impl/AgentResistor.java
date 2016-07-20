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

import org.slf4j.Logger;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.features.social.ISkillSocial;
import fr.irit.smac.amasfactory.agent.impl.Agent;
import fr.irit.smac.demoamasfactory.agent.features.IMyCommonFeatures;
import fr.irit.smac.demoamasfactory.agent.features.dipole.IKnowledgeDipole.ETerminal;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.IKnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.ISkillResistor;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public class AgentResistor
    extends Agent<IMyCommonFeatures, IKnowledgeResistor, ISkillResistor<IKnowledgeResistor>>implements ITwoStepsAgent {

    public AgentResistor() {

        // Needed by Jackson
    }

    @Override
    public void setLogger(Logger logger) {
        super.setLogger(logger);
        commonFeatures.getFeaturePlot().getSkill().setLogger(logger);
    }

    @Override
    public void perceive() {

        ISkillSocial<IKnowledgeSocial> skillSocial = commonFeatures.getFeatureSocial().getSkill();
        IKnowledgeSocial knowledgeSocial = commonFeatures.getFeatureSocial().getKnowledge();

        knowledgeSocial.getMsgBox().getMsgs()
            .forEach(m -> skill.processMsg(skillSocial, m));
    }

    @Override
    public void decideAndAct() {

        ISkillSocial<IKnowledgeSocial> skillSocial = commonFeatures.getFeatureSocial().getSkill();
        IKnowledgeSocial knowledgeSocial = commonFeatures.getFeatureSocial().getKnowledge();
        ISkillPlot<IKnowledgePlot> skillPlot = commonFeatures.getFeaturePlot().getSkill();

        String id = commonFeatures.getFeatureBasic().getKnowledge().getId();

        knowledgeSocial.getPortMap().get(ETerminal.FIRST.getName()).getValue().iterator()
            .forEachRemaining(o -> knowledge.setFirstPotential((Double) o));
        knowledgeSocial.getPortMap().get(ETerminal.SECOND.getName()).getValue().iterator()
            .forEachRemaining(o -> knowledge.setSecondPotential((Double) o));

        skill.publishValues(skillPlot, id);

        if (knowledge.getFirstPotential() == null
            || knowledge.getSecondPotential() == null) {

            skillSocial.sendPortToTarget(ETerminal.FIRST.getName(), id);
            skillSocial.sendPortToTarget(ETerminal.SECOND.getName(), id);
        }

        skill.communicateIntensity(knowledgeSocial, skillSocial, id);
        skill.requestPotentialUpdate(knowledgeSocial, skillSocial, id);

        skillSocial.clearPortMap();
    }
}
