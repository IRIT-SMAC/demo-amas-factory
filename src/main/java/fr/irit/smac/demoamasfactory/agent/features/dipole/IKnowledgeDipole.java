package fr.irit.smac.demoamasfactory.agent.features.dipole;

import fr.irit.smac.amasfactory.agent.IKnowledge;

public interface IKnowledgeDipole extends IKnowledge {

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

    public Double getFirstPotential();

    public Double getSecondPotential();

    public void setFirstPotential(Double firstPotential);

    public void setSecondPotential(Double secondPotential);

    public void computeUfromV();

    public void computeIfromU();

}