package fr.irit.smac.demoamasfactory.infrastructure.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import fr.irit.smac.amasfactory.agent.IInfrastructureAgent;
import fr.irit.smac.amasfactory.impl.BasicAmasFactory;
import fr.irit.smac.demoamasfactory.agent.IAgent;
import fr.irit.smac.demoamasfactory.message.IMessage;

public class DemoFactoryInfrastructure<A extends IInfrastructureAgent<M>,M> extends BasicAmasFactory<A , M> {

    final static Logger LOGGER = Logger.getLogger(DemoFactoryInfrastructure.class.getName());

    @SuppressWarnings("unchecked")
    @Override
    public DemoInfrastructure createInfrastructure(
        InputStream configuration) {

        DemoInfrastructure infrastruture = null;
        LOGGER.info("Begin of the initialization of the infrastructure");
        try {
            infrastruture = (DemoInfrastructure) super.<IAgent, IMessage> createInfrastructure(configuration);
            infrastruture.init();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        LOGGER.info("End of the initialization of the infrastructure");

        return infrastruture;
    }
}