package fr.irit.smac.demoamasfactory.infrastructure.impl;

import fr.irit.smac.amasfactory.impl.Infrastructure;
import fr.irit.smac.demoamasfactory.infrastructure.IDemoInfrastructure;
import fr.irit.smac.demoamasfactory.service.IMyServices;

@SuppressWarnings("rawtypes")
public class DemoInfrastructure<T extends IMyServices> extends Infrastructure<T>
    implements IDemoInfrastructure<T> {

    public DemoInfrastructure() {
        super();
    }
}
