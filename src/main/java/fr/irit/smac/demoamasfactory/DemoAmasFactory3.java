/*
 * #%L
 * demo-amas-factory
 * %%
 * Copyright (C) 2016 IRIT - SMAC Team and Brennus Analytics
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package fr.irit.smac.demoamasfactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasfactory.agent.IAgent;
import fr.irit.smac.amasfactory.agent.IKnowledge;
import fr.irit.smac.amasfactory.agent.ISkill;
import fr.irit.smac.amasfactory.agent.features.IFeature;
import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.amasfactory.agent.features.social.impl.Port;
import fr.irit.smac.amasfactory.agent.features.social.impl.Target;
import fr.irit.smac.amasfactory.service.agenthandler.impl.BasicAgentHandler;
import fr.irit.smac.amasfactory.service.execution.IExecutionService;
import fr.irit.smac.amasfactory.service.execution.impl.TwoStepAgExecutionService;
import fr.irit.smac.amasfactory.service.logging.impl.AgentLogLoggingService;
import fr.irit.smac.amasfactory.service.messaging.impl.MessagingService;
import fr.irit.smac.demoamasfactory.agent.features.IMyCommonFeatures;
import fr.irit.smac.demoamasfactory.agent.features.MyCommonFeatures;
import fr.irit.smac.demoamasfactory.agent.features.dipole.IKnowledgeDipole.ETerminal;
import fr.irit.smac.demoamasfactory.agent.features.dipole.generator.IKnowledgeUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.dipole.generator.impl.KnowledgeUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.dipole.generator.impl.SkillUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.IKnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.impl.KnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.impl.SkillResistor;
import fr.irit.smac.demoamasfactory.agent.features.node.IKnowledgeNode;
import fr.irit.smac.demoamasfactory.agent.features.node.impl.KnowledgeNode;
import fr.irit.smac.demoamasfactory.agent.features.node.impl.SkillNode;
import fr.irit.smac.demoamasfactory.agent.features.plot.IKnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.ISkillPlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.impl.KnowledgePlot;
import fr.irit.smac.demoamasfactory.agent.features.plot.impl.SkillPlot;
import fr.irit.smac.demoamasfactory.agent.impl.AgentNode;
import fr.irit.smac.demoamasfactory.agent.impl.AgentResistor;
import fr.irit.smac.demoamasfactory.agent.impl.AgentUGenerator;
import fr.irit.smac.demoamasfactory.infrastructure.IDemoInfrastructure;
import fr.irit.smac.demoamasfactory.infrastructure.impl.DemoFactory;
import fr.irit.smac.demoamasfactory.service.IMyServices;
import fr.irit.smac.demoamasfactory.service.impl.MyServices;
import fr.irit.smac.demoamasfactory.service.plot.impl.PlotService;
import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.ITwoStepsAgent;

public class DemoAmasFactory3 {

    private DemoAmasFactory3() {
        
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends IMyServices<A>, A extends IAgent<F, K, S>, F extends IMyCommonFeatures, K extends IKnowledge, S extends ISkill<K>> void main(
        String[] args)
            throws IOException {

        DemoFactory<T, A> demoFactory = new DemoFactory<>();

        IDemoInfrastructure<T> infra = demoFactory.createInfrastructure();

        infra.setServices((T) initServices());
        infra.getServices().getAgentHandlerService().setAgentMap((Map<String, A>) initAgentMap());
        infra.start();
        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().displaySimpleGui();
    }

    private static Map<String, IAgent<IMyCommonFeatures, IKnowledge, ISkill<IKnowledge>>> initAgentMap() {

        Map<String, IAgent<IMyCommonFeatures, IKnowledge, ISkill<IKnowledge>>> agentMap = new HashMap<>();
        int rows = 10;
        int cols = 10;
        agentMap.put("gen 20V", createAgentUGenerator("gen 20V", "1_1", rows + "_" + cols, 20d));
        agentMap.put("gen 2", createAgentUGenerator("gen 2", "1_1", "5_5", 20d));

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                String nodeId = i + "_" + j;
                agentMap.put(nodeId, createAgentNode(nodeId));

                if (i != rows) {
                    final String firstTerminal = i + "_" + j;
                    final String secondTerminal = (i + 1) + "_" + j;
                    String resistorId = "R " + firstTerminal + "|" + secondTerminal;
                    agentMap.put(resistorId,
                        createAgentResistor(resistorId, firstTerminal, secondTerminal, new Double(i + j)));
                }
                if (j != cols) {
                    final String firstTerminal = i + "_" + j;
                    final String secondTerminal = i + "_" + (j + 1);
                    String resistorId = "R " + firstTerminal + "|" + secondTerminal;
                    agentMap.put(resistorId,
                        createAgentResistor(resistorId, firstTerminal, secondTerminal, new Double(i + j)));
                }
            }
        }

        return agentMap;
    }

    @SuppressWarnings("unchecked")
    private static <A extends IAgent<F, IKnowledge, ISkill<IKnowledge>>, F extends IMyCommonFeatures> IMyServices<A> initServices() {

        MyServices<A, F> services = new MyServices<>();
        services.setAgentHandlerService(new BasicAgentHandler<>());
        IExecutionService<ITwoStepsAgent> executionService = new TwoStepAgExecutionService<>();
        services.setExecutionService((IExecutionService<A>) executionService);
        services.setMessagingService(new MessagingService<>());
        services.setLoggingService(new AgentLogLoggingService<>());
        services.setPlotService(new PlotService());
        services.getExecutionService().setNbThreads(2);
        services.getPlotService()
            .setAgentsFilter(s -> "R 5_5|5_6".equals(s) || "5_5".equals(s) || "1_1".equals(s)
                || "10_10".equals(s) || "gen 20V".equals(s) || "gen 2".equals(s));

        return (IMyServices<A>) services;
    }

    @SuppressWarnings("unchecked")
    private static <F extends IMyCommonFeatures, K extends IKnowledge, S extends ISkill<K>> IAgent<F, K, S> createAgentNode(
        String id) {

        AgentNode agent = new AgentNode();
        agent.setCommonFeatures(new MyCommonFeatures());
        agent.setKnowledge(new KnowledgeNode());
        agent.setSkill(new SkillNode<IKnowledgeNode>());
        agent.getFeatures().initFeatureBasic(id);
        agent.getFeatures().initFeatureSocial();
        initFeaturePlot(agent.getFeatures());

        agent.getFeatures().getFeatureSocial().getKnowledge().getPortMap().put("port",
            new Port("port"));
        agent.getFeatures().getFeatureSocial().getKnowledge().getPortMap().put("potentialDirection",
            new Port("potentialDirection"));
        agent.getFeatures().getFeatureSocial().getKnowledge().getPortMap().put("intensity",
            new Port("intensity"));

        return (IAgent<F, K, S>) agent;
    }

    @SuppressWarnings("unchecked")
    private static <F extends IMyCommonFeatures, K extends IKnowledge, S extends ISkill<K>> IAgent<F, K, S> createAgentResistor(
        String id, String node1,
        String node2, double resistor) {

        IMyCommonFeatures commonFeatures = new MyCommonFeatures();
        AgentResistor agent = new AgentResistor();
        agent.setCommonFeatures(commonFeatures);
        agent.setKnowledge(new KnowledgeResistor(resistor));
        agent.setSkill(new SkillResistor<IKnowledgeResistor>());
        initFeaturePlot(commonFeatures);

        commonFeatures.initFeatureBasic(id);
        commonFeatures.initFeatureSocial();

        commonFeatures.getFeatureSocial().getKnowledge().getPortMap().put(ETerminal.FIRST.getName(),
            new Port(ETerminal.FIRST.getName()));
        commonFeatures.getFeatureSocial().getKnowledge().getPortMap().put(ETerminal.SECOND.getName(),
            new Port(ETerminal.SECOND.getName()));
        commonFeatures.getFeatureSocial().getKnowledge().getTargetMap().put(ETerminal.FIRST.getName(),
            new Target(node1, "port", ETerminal.FIRST.getName()));
        commonFeatures.getFeatureSocial().getKnowledge().getTargetMap().put(ETerminal.SECOND.getName(),
            new Target(node2, "port", ETerminal.SECOND.getName()));
        agent.getFeatures().getFeatureSocial().getKnowledge().getTargetMap().put(
            ETerminal.FIRST.getName() + "Intensity",
            new Target(node1, "intensity", null));
        agent.getFeatures().getFeatureSocial().getKnowledge().getTargetMap().put(
            ETerminal.SECOND.getName() + "Intensity",
            new Target(node2, "intensity", null));
        agent.getFeatures().getFeatureSocial().getKnowledge().getTargetMap().put(
            ETerminal.FIRST.getName() + "PotentialDirection",
            new Target(node1, "potentialDirection", null));
        agent.getFeatures().getFeatureSocial().getKnowledge().getTargetMap().put(
            ETerminal.SECOND.getName() + "PotentialDirection",
            new Target(node2, "potentialDirection", null));

        return (IAgent<F, K, S>) agent;
    }

    @SuppressWarnings({ "unchecked" })
    private static <F extends IMyCommonFeatures, K extends IKnowledge, S extends ISkill<K>> IAgent<F, K, S> createAgentUGenerator(
        String id, String node1, String node2, double tension) {

        AgentUGenerator agent = new AgentUGenerator();
        agent.setCommonFeatures(new MyCommonFeatures());
        agent.setKnowledge(new KnowledgeUGenerator(tension));
        agent.setSkill(new SkillUGenerator<IKnowledgeUGenerator>());
        agent.getFeatures().initFeatureBasic(id);
        agent.getFeatures().initFeatureSocial();
        initFeaturePlot(agent.getFeatures());

        agent.getFeatures().getFeatureSocial().getKnowledge().getPortMap().put(ETerminal.FIRST.getName(),
            new Port(ETerminal.FIRST.getName()));
        agent.getFeatures().getFeatureSocial().getKnowledge().getPortMap().put(ETerminal.SECOND.getName(),
            new Port(ETerminal.SECOND.getName()));
        agent.getFeatures().getFeatureSocial().getKnowledge().getTargetMap().put(ETerminal.FIRST.getName(),
            new Target(node1, "port", ETerminal.FIRST.getName()));
        agent.getFeatures().getFeatureSocial().getKnowledge().getTargetMap().put(ETerminal.SECOND.getName(),
            new Target(node2, "port", ETerminal.SECOND.getName()));
        agent.getFeatures().getFeatureSocial().getKnowledge().getTargetMap().put(
            ETerminal.FIRST.getName() + "PotentialDirection",
            new Target(node1, "potentialDirection", null));
        agent.getFeatures().getFeatureSocial().getKnowledge().getTargetMap().put(
            ETerminal.SECOND.getName() + "PotentialDirection",
            new Target(node2, "potentialDirection", null));

        return (IAgent<F, K, S>) agent;
    }

    private static void initFeaturePlot(
        IMyCommonFeatures features) {

        features.setFeaturePlot(new Feature<IKnowledgePlot, ISkillPlot<IKnowledgePlot>>());
        IFeature<IKnowledgePlot, ISkillPlot<IKnowledgePlot>> featurePlot = features.getFeaturePlot();
        featurePlot.setKnowledge(new KnowledgePlot());
        featurePlot.setSkill(new SkillPlot<IKnowledgePlot>());
    }
}
