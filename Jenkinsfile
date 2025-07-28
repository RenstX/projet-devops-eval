pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('SONAR_TOKEN')
        ANSIBLE_CREDS = credentials('ANSIBLE_SSH_CREDS')
    }

    stages {
        stage('0. Preparation') {
            steps {
                echo 'Nettoyage de l-espace de travail...'
                cleanWs()
            }
        }
        
        stage('1. Checkout') {
            steps {
                echo 'Clonage du dépôt Git...'
                checkout scm
            }
        }

        stage('2. SonarQube Analysis') {
            steps {
                echo 'Compilation et analyse SonarQube...'
                bat 'mkdir build'
                bat 'javac -d build HelloWorld.java'
                withSonarQubeEnv('SonarQube') {
                    bat "sonar-scanner.bat -Dsonar.login=${SONAR_TOKEN}"
                }
            }
        }

        stage('3. Prepare Ansible Target') {
            steps {
                echo 'Démarrage du conteneur cible pour Ansible...'
                bat 'docker run -d --name ansible-target -p 8080:80 -p 2222:22 ubuntu-ssh-target'
                sleep(10)
            }
        }

        stage('4. Deploy with Ansible') {
            steps {
                echo 'Lancement du déploiement Ansible...'
                withCredentials([usernamePassword(credentialsId: 'ANSIBLE_SSH_CREDS', usernameVariable: 'ANSIBLE_USER', passwordVariable: 'ANSIBLE_PASSWORD')]) {
                    bat 'wsl ansible-playbook -i ansible/inventory.ini ansible/deploy.yml --user %ANSIBLE_USER% --extra-vars "ansible_password=%ANSIBLE_PASSWORD%"'
                }
            }
        }

        stage('5. Verify Deployment') {
            steps {
                echo 'Vérification du déploiement...'
                bat 'curl http://localhost:8080/ | findstr /C:"ERNST Michael"'
            }
        }
    }

    post {
        always {
            echo 'Étape 6. Nettoyage de l-environnement...'
            bat 'docker stop ansible-target || echo "Container already stopped or does not exist."'
            bat 'docker rm ansible-target || echo "Container already removed or does not exist."'
        }
    }
}
