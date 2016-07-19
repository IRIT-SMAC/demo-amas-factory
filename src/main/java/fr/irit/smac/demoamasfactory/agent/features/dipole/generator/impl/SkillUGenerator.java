package fr.irit.smac.demoamasfactory.agent.features.dipole.generator.impl;

import fr.irit.smac.amasfactory.agent.features.social.IKnowledgeSocial;
import fr.irit.smac.amasfactory.agent.features.social.ISkillSocial;
import fr.irit.smac.amasfactory.agent.features.social.ITarget;
import fr.irit.smac.amasfactory.message.PortOfTargetMessage;
import fr.irit.smac.amasfactory.message.ValuePortMessage;
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
    public void compareVoltageWithGeneratorTension(IKnowledgeSocial knowledgeSocial,
        ISkillSocial<IKnowledgeSocial> skillSocial, String id) {

        // compare actual voltage with generator tension
        Double actualVoltage = knowledge.getSecondPotential()
            - knowledge.getFirstPotential();
        Double error = knowledge.getU() - actualVoltage;

        if (error > 0) {
            // ask to increase the actualVoltage
            requestUChange(knowledge.getFirstPotential(), knowledge.getSecondPotential(), id,
                ETerminal.FIRST.getName(),
                ETerminal.SECOND.getName(), knowledgeSocial, skillSocial);
        }
        else if (error < 0) {
            // ask to decrease the actualVoltage
            requestUChange(knowledge.getSecondPotential(), knowledge.getFirstPotential(), id,
                ETerminal.SECOND.getName(),
                ETerminal.FIRST.getName(), knowledgeSocial, skillSocial);
        }
    }

    private void requestUChange(Double lowerReceiver, Double greaterReceiver, String id, String idLowerReceiver,
        String idGreaterReceiver, IKnowledgeSocial knowledgeSocial, ISkillSocial<IKnowledgeSocial> skillSocial) {

        PotentialDirection greaterMsg = new PotentialDirection(
            EFeedback.GREATER, 100d, greaterReceiver, id);
        PotentialDirection lowerMsg = new PotentialDirection(
            EFeedback.LOWER, 100d, lowerReceiver, id);
        
        ITarget target = knowledgeSocial.getTargetMap().get(idGreaterReceiver + "PotentialDirection");
        String agentId = target.getAgentId();
        String portTarget = target.getPortTarget();
        knowledgeSocial.getMsgBox().send(new ValuePortMessage(portTarget,greaterMsg, id),
            agentId);
        
        ITarget target2 = knowledgeSocial.getTargetMap().get(idLowerReceiver + "PotentialDirection");
        String agentId2 = target2.getAgentId();
        String portTarget2 = target2.getPortTarget();
        knowledgeSocial.getMsgBox().send(new ValuePortMessage(portTarget2,lowerMsg, id),
            agentId2);
        
//        knowledgeSocial.getTargetMap().get(idGreaterReceiver).setValue(greaterMsg);
//        knowledgeSocial.getTargetMap().get(idLowerReceiver).setValue(lowerMsg);
//        skillSocial.sendValueToTargets(id);
        logger.debug("Sent msg: UP: " + idGreaterReceiver + " DOWN: " + idLowerReceiver);
    }
}
