package fr.irit.smac.demoamasfactory.infrastructure.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import fr.irit.smac.amasfactory.impl.BasicAmasFactory;

public class DemoFactoryInfrastructure extends BasicAmasFactory {

    final static Logger LOGGER = Logger.getLogger(DemoFactoryInfrastructure.class.getName());

    @Override
    public DemoInfrastructure createInfrastructure(
        InputStream configuration) {

        LOGGER.info("Begin of the initialization of the infrastructure");
        DemoInfrastructure infrastructure = null;
        try {
            infrastructure = (DemoInfrastructure) super.createInfrastructure(configuration);
            infrastructure.init();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        LOGGER.info("End of the initialization of the infrastructure");

        return infrastructure;
    }
}