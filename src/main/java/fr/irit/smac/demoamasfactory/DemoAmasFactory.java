package fr.irit.smac.demoamasfactory;

import java.io.IOException;
import java.util.logging.Logger;

import fr.irit.smac.demoamasfactory.infrastructure.impl.DemoFactoryInfrastructure;
import fr.irit.smac.demoamasfactory.infrastructure.impl.DemoInfrastructure;

public class DemoAmasFactory {

    final static Logger LOGGER = Logger.getLogger(DemoAmasFactory.class.getName());

    public static void main(String[] args) throws IOException {

        DemoFactoryInfrastructure demoFactory = new DemoFactoryInfrastructure();

        DemoInfrastructure infra = demoFactory.createInfrastructure(
            ClassLoader.getSystemResourceAsStream("config.json"));
        
        infra.getExecutionService().step();
        infra.getExecutionService().step();
        infra.getExecutionService().step();
    }
}