package fr.irit.smac.demoamasfactory.infrastructure.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import fr.irit.smac.amasfactory.impl.AmasFactory;
import fr.irit.smac.demoamasfactory.infrastructure.IDemoInfrastructure;
import fr.irit.smac.demoamasfactory.service.IMyServices;

public class DemoFactory<T extends IMyServices<A>,A> extends AmasFactory<T, A> {

    final static Logger LOGGER = Logger.getLogger(DemoFactory.class.getName());

    @Override
    public IDemoInfrastructure<T, A> createInfrastructure(
        InputStream configuration) {

        LOGGER.info("Begin of the initialization of the infrastructure");
        DemoInfrastructure<T, A> infrastructure = null;
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

    @Override
    public IDemoInfrastructure<T, A> createInfrastructure() {
        return new DemoInfrastructure<>();
    }

}