pipeline {
    agent any

    tools {
        maven 'maven3.9'
    }

    environment {
    JAVA_HOME = '/usr/lib/jvm/java-1.21.0-openjdk-amd64'
    PATH = "/usr/lib/jvm/java-1.21.0-openjdk-amd64/bin:${env.PATH}"
    }

    stages {
        stage('Code Pull') {
            steps {
                git branch: 'main', url: 'https://github.com/armanshaikh98713-create/Java-backend.git'
            }
        }
        stage('Debug Java') {
            steps {
                sh 'echo $JAVA_HOME'
                sh 'java -version'
                sh 'mvn -version'
            }
        }
        stage('Building Code') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}