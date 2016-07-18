package fr.irit.smac.demoamasfactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasfactory.agent.IAgent;
import fr.irit.smac.amasfactory.agent.features.IFeature;
import fr.irit.smac.amasfactory.agent.features.impl.Feature;
import fr.irit.smac.amasfactory.agent.features.social.impl.Port;
import fr.irit.smac.amasfactory.agent.features.social.impl.Target;
import fr.irit.smac.amasfactory.service.agenthandler.impl.BasicAgentHandler;
import fr.irit.smac.amasfactory.service.execution.impl.TwoStepAgExecutionService;
import fr.irit.smac.amasfactory.service.logging.impl.AgentLogLoggingService;
import fr.irit.smac.amasfactory.service.messaging.impl.MessagingService;
import fr.irit.smac.demoamasfactory.agent.features.IMyCommonFeatures;
import fr.irit.smac.demoamasfactory.agent.features.MyCommonFeatures;
import fr.irit.smac.demoamasfactory.agent.features.dipole.generator.IKnowledgeUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.dipole.generator.ISkillUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.dipole.generator.impl.KnowledgeUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.dipole.generator.impl.SkillUGenerator;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.IKnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.ISkillResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.impl.KnowledgeResistor;
import fr.irit.smac.demoamasfactory.agent.features.dipole.resistor.impl.SkillResistor;
import fr.irit.smac.demoamasfactory.agent.features.node.IKnowledgeNode;
import fr.irit.smac.demoamasfactory.agent.features.node.ISkillNode;
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) throws IOException {

        DemoFactory demoFactory = new DemoFactory();

        IDemoInfrastructure<IMyServices> infra = (IDemoInfrastructure<IMyServices>) demoFactory.createInfrastructure();

        initServices(infra);
        infra.getServices().getAgentHandlerService().setAgentMap(initAgentMap());
        infra.start();
        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().displaySimpleGui();
    }

    @SuppressWarnings("rawtypes")
    private static Map<String, IAgent> initAgentMap() {

        Map<String, IAgent> agentMap = new HashMap<>();
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void initServices(IDemoInfrastructure<IMyServices> infra) {

        infra.setServices(new MyServices<>());
        infra.getServices().setAgentHandlerService(new BasicAgentHandler<>());
        infra.getServices().setExecutionService(new TwoStepAgExecutionService<>());
        infra.getServices().setMessagingService(new MessagingService<>());
        infra.getServices().setLoggingService(new AgentLogLoggingService<>());
        infra.getServices().setPlotService(new PlotService());
        infra.getServices().getExecutionService().setNbThreads(2);
        infra.getServices().getPlotService()
            .setAgentsFilter(s -> "R 5_5|5_6".equals(s) || "5_5".equals(s) || "1_1".equals(s)
                || "10_10".equals(s) || "gen 20V".equals(s) || "gen 2".equals(s));
    }

    private static AgentNode<IMyCommonFeatures, IKnowledgeNode, ISkillNode<IKnowledgeNode>> createAgentNode(
        String id) {

        AgentNode<IMyCommonFeatures, IKnowledgeNode, ISkillNode<IKnowledgeNode>> agent = new AgentNode<>();
        agent.setCommonFeatures(new MyCommonFeatures());
        agent.setKnowledge(new KnowledgeNode());
        agent.setSkill(new SkillNode<IKnowledgeNode>());
        agent.getFeatures().initFeatureBasic(id);
        agent.getFeatures().initFeatureSocial();
        initFeaturePlot(agent.getFeatures());

        try {
            agent.getFeatures().getFeatureSocial().getKnowledge().getPortMap().put("port",
                new Port("port", Class.forName("java.lang.String")));
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return agent;
    }

    private static AgentResistor<IMyCommonFeatures, IKnowledgeResistor, ISkillResistor<IKnowledgeResistor>> createAgentResistor(
        String id, String node1,
        String node2, double resistor) {

        AgentResistor<IMyCommonFeatures, IKnowledgeResistor, ISkillResistor<IKnowledgeResistor>> agent = new AgentResistor<>();
        agent.setCommonFeatures(new MyCommonFeatures());
        agent.setKnowledge(new KnowledgeResistor(resistor));
        agent.setSkill(new SkillResistor<IKnowledgeResistor>());
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
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return agent;
    }

    private static AgentUGenerator<IMyCommonFeatures, IKnowledgeUGenerator, ISkillUGenerator<IKnowledgeUGenerator>> createAgentUGenerator(
        String id, String node1, String node2, double tension) {

        AgentUGenerator<IMyCommonFeatures, IKnowledgeUGenerator, ISkillUGenerator<IKnowledgeUGenerator>> agent = new AgentUGenerator<>();
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
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return agent;
    }

    private static void initFeaturePlot(
        IMyCommonFeatures features) {

        features.setFeaturePlot(new Feature<IKnowledgePlot, ISkillPlot<IKnowledgePlot>>());
        IFeature<IKnowledgePlot, ISkillPlot<IKnowledgePlot>> featurePlot = features.getFeaturePlot();
        featurePlot.setKnowledge(new KnowledgePlot());
        featurePlot.setSkill(new SkillPlot<IKnowledgePlot>());
    }
}
