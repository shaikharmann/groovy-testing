pipeline {
    agent any

    tools {
        maven 'maven3.9'
        jdk 'Java11'

    }

 

    stages {
        stage('Code Pull') {
            steps {
                git branch: 'main', url: 'https://github.com/armanshaikh98713-create/Java-backend.git'
            }
        }
        // stage('Debug Java') {
        //     steps {
        //         sh 'echo $JAVA_HOME'
        //         sh 'java -version'
        //         sh 'mvn -version'
        //     }
        // }
        stage('Building Code') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('sending code'){
            steps{
                sshagent(['nginx']){
                    sh "scp -o  StrictHostKeyChecking=no target/*.war ubuntu@13.201.26.255:/tmp"
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@13.201.26.255 'mv /tmp/*.war /opt/tomcat/webapps/' " 
                }
        } }



    }
}