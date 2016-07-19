package fr.irit.smac.demoamasfactory.infrastructure.impl;

import fr.irit.smac.amasfactory.impl.Infrastructure;
import fr.irit.smac.demoamasfactory.infrastructure.IDemoInfrastructure;
import fr.irit.smac.demoamasfactory.service.IMyServices;

public class DemoInfrastructure<T extends IMyServices<A>, A> extends Infrastructure<T, A>
    implements IDemoInfrastructure<T, A> {

    public DemoInfrastructure() {
        super();
    }
}
