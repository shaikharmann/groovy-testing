pipeline {
    agent any

    tools {
        maven 'maven3.9'
        jdk 'Java11'

    }

     environment {
       IP = "13.206.83.113"
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
                    sh "scp -o  StrictHostKeyChecking=no -r * ubuntu@${IP}:/home/ubuntu"
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@${IP} 'docker build -t shkarman12/java-docker:v.${BUILD_ID} /home/ubuntu/.' " 
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@${IP} 'docker stop docker-java' " 
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@${IP} 'docker rm docker-java' "
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@${IP} 'docker run -d -p 7861:8080 --name docker-java java-docker' " 
                }
        } }

        stage('Pushing to DockerHub'){
            steps{
                sshagent(['Docker']){
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@${IP} 'docker push shkarman12/java-docker:v.${BUILD_ID}' " 
                }
        } }



    }
}