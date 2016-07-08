package fr.irit.smac.demoamasfactory.infrastructure.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasfactory.impl.BasicInfrastructure;
import fr.irit.smac.amasfactory.service.IServices;
import fr.irit.smac.demoamasfactory.infrastructure.IDemoInfrastructure;

public class DemoInfrastructure<T extends IServices<A>, A> extends BasicInfrastructure<T,A>
    implements IDemoInfrastructure<T,A> {

    public DemoInfrastructure() {
        super();
    }
    
    public DemoInfrastructure(
        @JsonProperty(value = "services", required = true) T services) {
        super(services);

    }
}
