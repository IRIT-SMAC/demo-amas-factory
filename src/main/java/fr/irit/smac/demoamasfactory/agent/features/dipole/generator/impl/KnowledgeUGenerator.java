package fr.irit.smac.demoamasfactory.agent.features.dipole.generator.impl;

import fr.irit.smac.demoamasfactory.agent.features.dipole.generator.IKnowledgeUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.dipole.impl.KnowledgeDipole;

public class KnowledgeUGenerator extends KnowledgeDipole implements IKnowledgeUGenerator {

    private static final long serialVersionUID = 1L;

    public KnowledgeUGenerator() {
        super();
    }

    @Override
    public void computeUfromV() {
        // do nothing, it's a fixed U generator
    }
}
