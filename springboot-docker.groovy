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

        stage('Build N Deploy'){
            steps{
                sshagent(['Docker']){
                    sh "scp -o  StrictHostKeyChecking=no -r * ubuntu@3.108.185.143:/home/ubuntu"
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@3.108.185.143 'docker build -t shkarman12/java-docker /home/ubuntu/.' " 
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@3.108.185.143 'docker stop docker-java' " 
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@3.108.185.143 'docker rm docker-java' " 
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@3.108.185.143 'docker run -d -p 7861:8080 --name docker-java java-docker' " 
                }
        } }

        stage('Pushing to DockerHub'){
            steps{
                sshagent(['Docker']){
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@3.108.185.143 'docker push shkarman12/java-docker:v.${BUILD_ID}' " 
                }
        } }



    }
}