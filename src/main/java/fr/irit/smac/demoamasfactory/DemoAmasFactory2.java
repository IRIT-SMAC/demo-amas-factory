package fr.irit.smac.demoamasfactory;

import java.io.IOException;
import java.util.logging.Logger;

import fr.irit.smac.amasfactory.service.IServices;
import fr.irit.smac.demoamasfactory.infrastructure.impl.DemoFactoryInfrastructure;
import fr.irit.smac.demoamasfactory.infrastructure.impl.DemoInfrastructure;

public class DemoAmasFactory2 {

    final static Logger LOGGER = Logger.getLogger(DemoAmasFactory2.class.getName());

    public static <T extends IServices<A>, A> void main(String[] args) throws IOException {

        DemoFactoryInfrastructure demoFactory = new DemoFactoryInfrastructure();

        DemoInfrastructure<T,A> infra = (DemoInfrastructure<T, A>) demoFactory.createInfrastructure(
            ClassLoader.getSystemResourceAsStream("config2.json"));
        
        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().step();
    }
}