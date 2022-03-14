pipeline {
    agent any
    stages {
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
