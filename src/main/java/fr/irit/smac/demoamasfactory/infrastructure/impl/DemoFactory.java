package fr.irit.smac.demoamasfactory.infrastructure.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import fr.irit.smac.amasfactory.IInfrastructure;
import fr.irit.smac.amasfactory.impl.AmasFactory;
import fr.irit.smac.demoamasfactory.service.IMyServices;

@SuppressWarnings("rawtypes")
public class DemoFactory extends AmasFactory {

    final static Logger LOGGER = Logger.getLogger(DemoFactory.class.getName());

    @SuppressWarnings("unchecked")
    @Override
    public IInfrastructure<IMyServices> createInfrastructure(
        InputStream configuration) {

        LOGGER.info("Begin of the initialization of the infrastructure");
        DemoInfrastructure<IMyServices> infrastructure = null;
        try {
            infrastructure = (DemoInfrastructure<IMyServices>) super.createInfrastructure(configuration);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        LOGGER.info("End of the initialization of the infrastructure");

        return infrastructure;
    }

    @Override
    public IInfrastructure<IMyServices> createInfrastructure() {
        return new DemoInfrastructure<>();
    }
}