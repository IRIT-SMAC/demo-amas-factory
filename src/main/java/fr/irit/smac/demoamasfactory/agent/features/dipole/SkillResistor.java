package fr.irit.smac.demoamasfactory.agent.features.dipole;

import fr.irit.smac.amasfactory.message.IMessage;
import fr.irit.smac.demoamasfactory.knowledge.IDipoleKnowledge.Terminal;
import fr.irit.smac.demoamasfactory.message.impl.DirectionRequest;
import fr.irit.smac.demoamasfactory.message.impl.IntensityDirectionRequest;
import fr.irit.smac.libs.tooling.avt.EFeedback;

public class SkillResistor<K extends KnowledgeResistor> extends SkillDipole<K> {

    @Override
    public void processMsg(IMessage m) {
        super.processMsg(m);
        if (m instanceof IntensityDirectionRequest) {
            IntensityDirectionRequest idr = (IntensityDirectionRequest) m;
            EFeedback direction = idr.direction;
            Double knownValue = idr.knownValue;
            if (idr.getSender().equals(knowledge.getId(Terminal.SECOND))) {
                direction = DirectionRequest.opposite(direction);
                knownValue = -knownValue;
            }

            if (this.knowledge.getI().equals(knownValue)) {
                if (this.knowledge.getWorstIntensityCriticality() < idr.criticality) {
                    this.knowledge.setWorstIntensityCriticality(idr.criticality);
                    this.knowledge.setIntensityDirection(direction);
//                    logger.debug("received I" + idr.direction + " from " + idr.getSender());
                }
            }
        }
    }
}
