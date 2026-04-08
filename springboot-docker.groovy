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

        stage('sending code'){
            steps{
                sshagent(['Docker']){
                    sh "scp -o  StrictHostKeyChecking=no -r * ubuntu@3.6.92.43:/home/ubuntu"
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@3.6.92.43 'docker build -t java-docker /home/ubuntu/.' " 
                }
        } }



    }
}