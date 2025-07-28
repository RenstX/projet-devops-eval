# Projet d'Évaluation DevOps

**Auteur :** ERNST Michael

## 1. Objectif

L'objectif de ce projet est de construire une chaîne CI/CD fonctionnelle avec Jenkins, SonarQube, Ansible, Docker et Kubernetes, en partant d'un code source hébergé sur GitHub.

## 2. Architecture

Le pipeline CI/CD est conçu pour automatiser le cycle de vie de l'application : un `push` sur GitHub déclenche une analyse de code sur SonarQube, suivie d'un déploiement sur un conteneur Docker avec Ansible, et sur un cluster Kubernetes.

Les briques technologiques ont été validées individuellement. L'intégration finale dans un pipeline Jenkins unique a présenté des défis de configuration liés à l'environnement Windows et WSL.

## 3. Outils Requis
* Windows & WSL (Sous-système Windows pour Linux)
* Docker Desktop
* Jenkins
* SonarQube
* Ansible (installé dans WSL)
* kubectl

## 4. Commandes et Étapes Clés

### SonarQube
Configuration du projet pour l'analyse (`sonar-project.properties`) :
```properties
sonar.projectKey=projet-eval-devops
sonar.projectName=Evaluation DevOps - Projet Java
sonar.projectVersion=1.0
sonar.sources=.
sonar.java.binaries=build
sonar.sourceEncoding=UTF-8
