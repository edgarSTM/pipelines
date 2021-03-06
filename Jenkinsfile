pipeline {
    agent none
    stages {
        stage('Build') {
        agent {
        docker {
            image 'maven:3.8.1-adoptopenjdk-11'
            args '-v /root/.m2:/root/.m2'
            }
        }
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            agent {
            docker {
                image 'maven:3.8.1-adoptopenjdk-11'
                args '-v /root/.m2:/root/.m2'
                }
        }
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
            agent any
            steps {
                script {
                  sh 'docker build -t edgarstm/hola-app:latest ./'
                }
            }
        }
        stage('Deploy Docker Image') {
            agent any
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
      agent any
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
