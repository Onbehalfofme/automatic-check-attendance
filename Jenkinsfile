pipeline{
    agent any
    stages {
        stage('Checkout') {
            steps {
            git credentialsId: '5cc91d06-bd21-47c0-ba5f-6083f51afa49', url: 'git@github.com:Onbehalfofme/automatic-check-attendance.git', branch: 'master'
            }
        }
        stage('Build') {
            steps {
                sh 'cd backend/ && ./gradlew bootJar && cd ../'
            }
        }
    }
}
