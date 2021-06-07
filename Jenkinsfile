pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'

                // Run Maven on a Unix agent.
                sh "mvn -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        stage('Package') {
            steps {
                //build docker image grom created artifact and store
                sh "sudo docker build . -t dyltra/user-service"
                sh "sudo docker push dyltra/user-service"
            }
        }
        stage('Deploy') {
            steps {
                //remove previous version
                sh "sudo docker stop user-service-container"
                sh "sudo docker rm user-service-container"
                sh "sudo docker rmi dyltra/user_service:latest"
                sh "sudo docker run -p 8080:8080 dyltra/user_service:latest -t user=service-container"
                
            }
        }
    }
}
