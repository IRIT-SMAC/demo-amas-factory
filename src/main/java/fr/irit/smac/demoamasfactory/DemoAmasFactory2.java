package fr.irit.smac.demoamasfactory;

import java.io.IOException;
import java.util.logging.Logger;

import fr.irit.smac.demoamasfactory.infrastructure.IDemoInfrastructure;
import fr.irit.smac.demoamasfactory.infrastructure.impl.DemoFactory;
import fr.irit.smac.demoamasfactory.service.IMyServices;

public class DemoAmasFactory2 {

    final static Logger LOGGER = Logger.getLogger(DemoAmasFactory2.class.getName());

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws IOException {

        DemoFactory demoFactory = new DemoFactory();

        IDemoInfrastructure<IMyServices> infra = (IDemoInfrastructure<IMyServices>) demoFactory.createInfrastructure(
            ClassLoader.getSystemResourceAsStream("config2.json"));
        
        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().displaySimpleGui();
    }
}