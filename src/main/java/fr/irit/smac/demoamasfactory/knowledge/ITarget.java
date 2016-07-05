package fr.irit.smac.demoamasfactory.knowledge;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public interface ITarget {
	/**
	 * @return the target agent id
	 */
	public String getAgentId();

	/**
	 * @return the target agent port id
	 */
	public String getInputId();
}
