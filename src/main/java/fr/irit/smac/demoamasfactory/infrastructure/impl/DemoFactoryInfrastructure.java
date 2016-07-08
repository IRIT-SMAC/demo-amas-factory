package fr.irit.smac.demoamasfactory.infrastructure.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import fr.irit.smac.amasfactory.IInfrastructure;
import fr.irit.smac.amasfactory.impl.BasicAmasFactory;
import fr.irit.smac.amasfactory.service.IServices;

public class DemoFactoryInfrastructure extends BasicAmasFactory {

    final static Logger LOGGER = Logger.getLogger(DemoFactoryInfrastructure.class.getName());

    @Override
    public <T extends IServices<A>, A> IInfrastructure<T,A> createInfrastructure(
        InputStream configuration) {

        LOGGER.info("Begin of the initialization of the infrastructure");
        DemoInfrastructure<T,A> infrastructure = null;
        try {
            infrastructure = (DemoInfrastructure<T, A>) super.createInfrastructure(configuration);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        LOGGER.info("End of the initialization of the infrastructure");

        return infrastructure;
    }
}