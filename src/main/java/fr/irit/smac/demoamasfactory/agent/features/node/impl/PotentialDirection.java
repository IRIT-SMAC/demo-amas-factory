package fr.irit.smac.demoamasfactory.agent.features.node.impl;

import fr.irit.smac.libs.tooling.avt.EFeedback;

public class PotentialDirection {

    public EFeedback direction;
    public Double    criticality;
    public Double         knownValue;

    public PotentialDirection(EFeedback direction,
        Double criticality, Double knownValue) {
        this.direction = direction;
        this.criticality = criticality;
        this.knownValue = knownValue;
    }

    public static EFeedback opposite(EFeedback direction) {
        switch (direction) {
            case GREATER:
                return EFeedback.LOWER;
            case LOWER:
                return EFeedback.GREATER;

            default:
                return EFeedback.GOOD;
        }
    }
}
