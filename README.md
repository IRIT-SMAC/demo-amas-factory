# demo-amas-factory
A demonstration project for amas-factory

[![Build Status](https://travis-ci.org/IRIT-SMAC/demo-amas-factory.svg?branch=develop)](https://travis-ci.org/IRIT-SMAC/demo-amas-factory)

- [Github] (https://github.com/IRIT-SMAC/demo-amas-factory)
- [Travis] (https://travis-ci.org/IRIT-SMAC/demo-amas-factory)

Ce projet correspond à un système multi-agent coopératif (AMAS) simulant un réseau électrique.

Dans ce système, il existe trois types d'agent :

- Un agent noeud qui relie les composants entre eux. Cet agent correspond à la classe AgentNode. KnowledgeNode et SkillNode sont les implémentations de son knowledge et de son skill.
- Un agent résistance. Cet agent correspond à la classe AgentResistor. KnowledgeResistor et SkillResistor sont les implémentations de son knowledge et de son skill.
- Un agent générateur. Cet agent correspond à la classe AgentUGenerator. KnowledgeUGenerator et SkillUGenerator sont les implémentations de son knowledge et de son skill. 

Ces deux derniers agents sont tous les deux des agents dipoles. En effet, ils ont quelques caractéristiques en commun, ce qui explique le fait que leur knowledge et leur skill héritent tout deux de KnowledgeDipole et SkillDipole.

En plus d'utiliser la plupart des services déjà implémentés dans amas-factory, le système utilise un nouveau service : plotService, qui permet d'afficher certaines valeurs des différents agents. Ce service a été implémenté et ajouté à la liste des services. Pour étendre cette liste, la classe MyServices a été créée.
Ainsi, chaque agent dispose d'un graphique sur lequel il peut publier ses valeurs. Pour cela, les agents disposent d'une feature plot. Cette feature a été implémentée et ajoutée à la liste des features communes à chaque agent. Pour étendre cette liste, la classe MyCommonFeatures a été créée.