pipeline{
    agent any
    stages {
        stage('Checkout') {
            steps {
            git credentialsId: 'jenkins-private', url: 'git@github.com:Onbehalfofme/automatic-check-attendance.git', branch: 'master'
            }
        }
        stage('Build') {
            steps {
                sh 'cd backend/ && ./gradlew bootJar && cd ../'
            }
        }
    }
}
