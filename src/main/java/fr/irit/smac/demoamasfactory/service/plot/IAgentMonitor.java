package fr.irit.smac.demoamasfactory.service.plot;

import java.util.function.Predicate;

public interface IAgentMonitor {

    public void publish(String name, Double value);
    
    public void init(String id, Predicate<String> agentsFilter, Predicate<String> valuesFilter);

    public void setAgentsFilter(Predicate<String> agentsFilter);
}
