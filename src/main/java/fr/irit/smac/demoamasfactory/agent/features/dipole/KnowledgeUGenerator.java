package fr.irit.smac.demoamasfactory.agent.features.dipole;

public class KnowledgeUGenerator extends KnowledgeDipole {

    private static final long serialVersionUID = 1L;

    public KnowledgeUGenerator() {
        super();
    }

    @Override
    public void computeUfromV() {
        // do nothing, it's a fixed U generator
    }
}
