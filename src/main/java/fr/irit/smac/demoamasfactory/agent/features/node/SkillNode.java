package fr.irit.smac.demoamasfactory.agent.features.node;

import fr.irit.smac.amasfactory.agent.impl.Skill;
import fr.irit.smac.demoamasfactory.agent.features.plot.KnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.SkillPlot;
import fr.irit.smac.libs.tooling.avt.EFeedback;

public class SkillNode<K extends KnowledgeNode> extends Skill<K> {

    public void publishValue(SkillPlot<KnowledgePlot> skillPlot, String id) {
        
        skillPlot.publish("potential",
            this.knowledge.potential.getValue(), id);
        // logger.debug("potential: " +
        // knowledgeNode.potential.getValue());
        if (this.knowledge.intensities.get("R1") != null) {
            skillPlot.publish("R1", this.knowledge.intensities.get("R1"), id);
        }
        if (this.knowledge.intensities.get("R2") != null) {
            skillPlot.publish("R2", this.knowledge.intensities.get("R2"), id);
        }
        if (this.knowledge.intensities.get("R3") != null) {
            skillPlot.publish("R3", this.knowledge.intensities.get("R3"), id);
        }
    }
    
    public void applyKirchhoffLaw() {
        
        Double intensitiesSum = 0d;
        for (Double intensity : this.knowledge.intensities.values()) {
            intensitiesSum += intensity;
        }
        if (!this.knowledge.isReceivedPdr() && intensitiesSum != 0) {
            if (this.knowledge.potentialDirection == null && !this.knowledge.getPreviousISumChange().equals(intensitiesSum)) {
//                logger.debug("Isum: " + intensitiesSum + " previousChange: " + previousISumChange);
                this.knowledge.setPreviousISumChange(intensitiesSum);
                if (intensitiesSum > 0) {
                    this.knowledge.potential.adjustValue(EFeedback.GREATER);
                }
                else if (intensitiesSum < 0) {
                    this.knowledge.potential.adjustValue(EFeedback.LOWER);
                }
            }
        }
    }

    public void adjustPotential() {
        if (this.knowledge.potentialDirection != null) {
            // logger.debug(
            // "potential=" + this.knowledge.potential.getValue() + " adjust it
            // " +
            // knowledgeNode.potentialDirection);
            this.knowledge.potential.adjustValue(this.knowledge.potentialDirection);
        }
    }
    
    public void cleanKnowledge() {
        
        this.knowledge.potentialDirection = null;
        this.knowledge.worstPotentialCriticality = 0d;
        this.knowledge.setReceivedPdr(true);

    }
}
