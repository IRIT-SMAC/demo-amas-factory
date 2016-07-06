package fr.irit.smac.demoamasfactory.knowledge;

import fr.irit.smac.amasfactory.agent.IKnowledge;

public interface IDipoleKnowledge extends IKnowledge {

    public enum Terminal {

        FIRST("firstTerminal"), SECOND("secondTerminal");

        String name;

        private Terminal(String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
    }

    /**
     * @return the tension
     */
    public Double getU();

    /**
     * @return the resistor
     */
    public Double getR();

    /**
     * @return the intensity
     */
    public Double getI();

}