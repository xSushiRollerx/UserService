pipeline {
    agent any
    environment {
        COMMIT_HASH = "${sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()}"
        AWS_ID = credentials('aws-id')
        IMG_NAME = "user-service"
        REPO_URL = credentials('service-user')
    }

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
        stage('Clean target') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('Build') {
            steps {
                // Run Maven on a Unix agent.
                sh "mvn test package"

            }
        }
        // stage('Code Analysis: Sonarqube') {
        //     steps {
        //         withSonarQubeEnv('SonarQube') {
        //             sh 'mvn sonar:sonar'
        //         }
        //     }east
        // }
        // stage('Await Quality Gateway') {
        //     steps {
        //         waitForQualityGate abortPipeline: true
        //     }
        // }
        stage("Docker Build") {
            steps {
                echo "Docker Build...."
                //sh "awsv2 --install"
                sh "awsv2 --version"
                //sh "docker login -u AWS --password-stdin \$(aws ecr get-login-password --region us-west-1) ${AWS_ID}.dkr.ecr.us-west-1.amazonaws.com"
                sh 'awsv2 ecr get-login-password --region us-west-1 | docker login --username AWS --password-stdin ${AWS_ID}.dkr.ecr.us-west-1.amazonaws.com'
                sh "docker build --tag ${IMG_NAME}:${COMMIT_HASH} ."
                sh 'docker tag ${IMG_NAME}:${COMMIT_HASH} ${AWS_ID}.dkr.ecr.us-west-1.amazonaws.com/${REPO_URL}:${COMMIT_HASH}'
                echo "Docker Push..."
                sh 'docker push ${AWS_ID}.dkr.ecr.us-west-1.amazonaws.com/${REPO_URL}:${COMMIT_HASH}'
            }
        }
        stage("Deploy") {
            steps {
                echo "Deploying cloudformation.."
                sh "awsv2 cloudformation deploy --debug --stack-name UserMsStack --template-file ./ecs.yaml --parameter-overrides ApplicationName=${IMG_NAME} ApplicationEnvironment=dev ECRRepositoryUri=635496629433.dkr.ecr.us-west-1.amazonaws.com/user-service --capabilities CAPABILITY_IAM CAPABILITY_NAMED_IAM --region us-west-1"
            }
        }
    }
    post {
        always {
            sh 'mvn clean'
            sh "docker system prune -f"
        }
    }
}
