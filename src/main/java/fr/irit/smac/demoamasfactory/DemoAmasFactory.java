package fr.irit.smac.demoamasfactory;

import java.io.IOException;
import java.util.logging.Logger;

import fr.irit.smac.demoamasfactory.infrastructure.IDemoInfrastructure;
import fr.irit.smac.demoamasfactory.infrastructure.impl.DemoFactory;
import fr.irit.smac.demoamasfactory.service.IMyServices;

public class DemoAmasFactory {

    final static Logger LOGGER = Logger.getLogger(DemoAmasFactory.class.getName());

    public static <T extends IMyServices<A>, A> void main(String[] args) throws IOException {

        DemoFactory<T, A> demoFactory = new DemoFactory<>();

        IDemoInfrastructure<T, A> infra = demoFactory.createInfrastructure(
            ClassLoader.getSystemResourceAsStream("config.json"));

        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().displaySimpleGui();
    }
}