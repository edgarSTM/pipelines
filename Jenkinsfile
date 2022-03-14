pipeline {
    agent {
        docker {
            image 'maven:3.8.1-adoptopenjdk-11'
            args '-v /root/.m2:/root/.m2'
        }
        docker {
            image 'docker:dind'
            args '--detach --privileged --network jenkins --env DOCKER_TLS_CERTDIR=/certs --volume jenkins-docker-certs:/certs/client --volume jenkins-data:/var/jenkins_home --publish 3000:3000 --publish 2376:2376'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                  sh 'docker build -t edgarstm/hola-app:latest ./'
                }
            }
        }
        stage('Deploy Docker Image') {
            steps {
                script {
                 withCredentials([usernamePassword(credentialsId: 'dockerhub-pwd', passwordVariable: 'passhub', usernameVariable: 'edgarstm')]) {
                    sh 'docker login -u edgarstm -p ${passhub}'
                 }  
                 sh 'docker push edgarstm/hola-app:latest'
                }
            }
        }
    
    stage('Deploy App on k8s') {
      steps {
            script {
                try{
                    kubernetesDeploy(configs: "hola-app-deploy.yaml", kubeconfigId: "kubekeys")
                    }catch(error){
                    sh "echo sin_despliege "
                        }
                    }
            }
    }
    }
}
