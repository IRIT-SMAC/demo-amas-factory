package fr.irit.smac.demoamasfactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasfactory.agent.IKnowledge;
import fr.irit.smac.amasfactory.agent.ISkill;
import fr.irit.smac.amasfactory.agent.features.IFeature;
import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.amasfactory.agent.features.social.impl.Port;
import fr.irit.smac.amasfactory.agent.features.social.impl.Target;
import fr.irit.smac.amasfactory.agent.impl.TwoStepAgent;
import fr.irit.smac.amasfactory.service.agenthandler.impl.BasicAgentHandler;
import fr.irit.smac.amasfactory.service.execution.impl.TwoStepAgExecutionService;
import fr.irit.smac.amasfactory.service.logging.impl.AgentLogLoggingService;
import fr.irit.smac.amasfactory.service.messaging.impl.MessagingService;
import fr.irit.smac.demoamasfactory.agent.features.IMyCommonFeatures;
import fr.irit.smac.demoamasfactory.agent.features.MyCommonFeatures;
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

public class DemoAmasFactory3 {

    @SuppressWarnings("unchecked")
    public static <T extends IMyServices<A>, A extends TwoStepAgent<F, K, S>, F extends IMyCommonFeatures, K extends IKnowledge, S extends ISkill<K>> void main(
        String[] args)
            throws IOException {

        DemoFactory<T, A> demoFactory = new DemoFactory<>();

        IDemoInfrastructure<T,A> infra = demoFactory.createInfrastructure();

        infra.setServices((T) initServices());
        infra.getServices().getAgentHandlerService().setAgentMap((Map<String, A>) initAgentMap());
        infra.start();
        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().displaySimpleGui();
    }

    private static Map<String, TwoStepAgent<IMyCommonFeatures, IKnowledge, ISkill<IKnowledge>>> initAgentMap() {

        Map<String, TwoStepAgent<IMyCommonFeatures, IKnowledge, ISkill<IKnowledge>>> agentMap = new HashMap<>();
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

    private static <A extends TwoStepAgent<F, IKnowledge, ISkill<IKnowledge>>, F extends IMyCommonFeatures> IMyServices<A> initServices() {

        MyServices<A,F> services = new MyServices<>();
        services.setAgentHandlerService(new BasicAgentHandler<>());
        services.setExecutionService(new TwoStepAgExecutionService<>());
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
    private static <F extends IMyCommonFeatures, K extends IKnowledge, S extends ISkill<K>> TwoStepAgent<F, K, S> createAgentNode(
        String id) {

        AgentNode agent = new AgentNode();
        agent.setCommonFeatures(new MyCommonFeatures());
        agent.setKnowledge(new KnowledgeNode());
        agent.setSkill(new SkillNode<IKnowledgeNode>());
        agent.getFeatures().initFeatureBasic(id);
        agent.getFeatures().initFeatureSocial();
        initFeaturePlot(agent.getFeatures());

        try {
            agent.getFeatures().getFeatureSocial().getKnowledge().getPortMap().put("port",
                new Port("port", Class.forName("java.lang.String")));
            agent.getFeatures().getFeatureSocial().getKnowledge().getPortMap().put("potentialDirection",
                new Port("potentialDirection", Class.forName("java.lang.String")));
            agent.getFeatures().getFeatureSocial().getKnowledge().getPortMap().put("intensity",
                new Port("intensity", Class.forName("java.lang.String")));
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return (TwoStepAgent<F, K, S>) agent;
    }

    @SuppressWarnings("unchecked")
    private static <F extends IMyCommonFeatures, K extends IKnowledge, S extends ISkill<K>> TwoStepAgent<F, K, S> createAgentResistor(
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

        try {
            commonFeatures.getFeatureSocial().getKnowledge().getPortMap().put("firstTerminal",
                new Port("firstTerminal", Class.forName("java.lang.String")));
            commonFeatures.getFeatureSocial().getKnowledge().getPortMap().put("secondTerminal",
                new Port("secondTerminal", Class.forName("java.lang.String")));
            commonFeatures.getFeatureSocial().getKnowledge().getTargetMap().put("firstTerminal",
                new Target(node1, "port", "firstTerminal"));
            commonFeatures.getFeatureSocial().getKnowledge().getTargetMap().put("secondTerminal",
                new Target(node2, "port", "secondTerminal"));
            agent.getFeatures().getFeatureSocial().getKnowledge().getTargetMap().put("firstTerminalIntensity",
                new Target(node1, "intensity", null));
            agent.getFeatures().getFeatureSocial().getKnowledge().getTargetMap().put("secondTerminalIntensity",
                new Target(node2, "intensity", null));

        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (TwoStepAgent<F, K, S>) agent;
    }

    @SuppressWarnings({ "unchecked" })
    private static <F extends IMyCommonFeatures, K extends IKnowledge, S extends ISkill<K>> TwoStepAgent<F, K, S> createAgentUGenerator(
        String id, String node1, String node2, double tension) {

        AgentUGenerator agent = new AgentUGenerator();
        agent.setCommonFeatures(new MyCommonFeatures());
        agent.setKnowledge(new KnowledgeUGenerator(tension));
        agent.setSkill(new SkillUGenerator<IKnowledgeUGenerator>());
        agent.getFeatures().initFeatureBasic(id);
        agent.getFeatures().initFeatureSocial();
        initFeaturePlot(agent.getFeatures());

        try {
            agent.getFeatures().getFeatureSocial().getKnowledge().getPortMap().put("firstTerminal",
                new Port("firstTerminal", Class.forName("java.lang.String")));
            agent.getFeatures().getFeatureSocial().getKnowledge().getPortMap().put("secondTerminal",
                new Port("secondTerminal", Class.forName("java.lang.String")));
            agent.getFeatures().getFeatureSocial().getKnowledge().getTargetMap().put("firstTerminal",
                new Target(node1, "port", "firstTerminal"));
            agent.getFeatures().getFeatureSocial().getKnowledge().getTargetMap().put("secondTerminal",
                new Target(node2, "port", "secondTerminal"));
            agent.getFeatures().getFeatureSocial().getKnowledge().getTargetMap().put("firstTerminalPotentialDirection",
                new Target(node1, "potentialDirection", null));
            agent.getFeatures().getFeatureSocial().getKnowledge().getTargetMap().put("secondTerminalPotentialDirection",
                new Target(node2, "potentialDirection", null));

        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (TwoStepAgent<F, K, S>) agent;
    }

    private static void initFeaturePlot(
        IMyCommonFeatures features) {

        features.setFeaturePlot(new Feature<IKnowledgePlot, ISkillPlot<IKnowledgePlot>>());
        IFeature<IKnowledgePlot, ISkillPlot<IKnowledgePlot>> featurePlot = features.getFeaturePlot();
        featurePlot.setKnowledge(new KnowledgePlot());
        featurePlot.setSkill(new SkillPlot<IKnowledgePlot>());
    }
}
