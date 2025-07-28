pipeline {
    agent any

    environment {
        // Récupère les secrets depuis les credentials Jenkins
        SONAR_TOKEN = credentials('SONAR_TOKEN')
        ANSIBLE_CREDS = credentials('ANSIBLE_SSH_CREDS')
    }

    stages {
        stage('1. Checkout') {
            steps {
                echo 'Clonage du dépôt Git...'
                // La commande 'checkout scm' clone automatiquement le dépôt configuré dans le job
                checkout scm
            }
        }

        stage('2. SonarQube Analysis') {
            steps {
                echo 'Compilation et analyse SonarQube...'
                // Étape de compilation pour Windows
                bat 'javac HelloWorld.java'
                
                // Exécute l'analyse SonarQube en utilisant le token
                withSonarQubeEnv('SonarQube') { // Nom du serveur dans Sonar dans Jenkins
                    bat "sonar-scanner.bat -Dsonar.login=${SONAR_TOKEN}"
                }
            }
        }

        stage('3. Deploy with Ansible') {
            steps {
                echo 'Lancement du déploiement Ansible...'
                // Cette étape exécute le playbook depuis WSL
                // Elle injecte l'utilisateur et le mot de passe dans la commande
                // Note: 'ansible_password' est récupéré de la variable ANSIBLE_CREDS_PSW
                ws('ansible') {
                    withCredentials([usernamePassword(credentialsId: 'ANSIBLE_SSH_CREDS', usernameVariable: 'ANSIBLE_USER', passwordVariable: 'ANSIBLE_PASSWORD')]) {
                        bat 'wsl ansible-playbook -i inventory.ini deploy.yml --user %ANSIBLE_USER% --extra-vars "ansible_password=%ANSIBLE_PASSWORD%"'
                    }
                }
            }
        }

        stage('4. Verify Deployment') {
            steps {
                echo 'Vérification du déploiement...'
                // Commande pour vérifier si la page contient le nom de l'étudiant
                bat 'curl http://localhost:8080/ | findstr /C:"[Michael ERNST]"'
            }
        }
    }

    post {
        always {
            echo 'Étape 5. Nettoyage de l\'environnement...'
            // Arrête et supprime le conteneur Docker à la fin du pipeline
            bat 'docker stop ansible-target'
            bat 'docker rm ansible-target'
        }
    }
}
