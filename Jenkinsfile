pipeline{
    agent any
    stages {
        stage('Checkout') {
            steps {
            git url: 'https://github.com/Onbehalfofme/automatic-check-attendance.git', branch: 'master'
            }
        }
         stage('Build') {
            steps {
                sh 'cd backend/ && ./gradlew build && cd ..'
            }
        }
        stage('Docker image build') {
            steps{
                sh 'docker build -t onbehalfofme/attendance:server .'
            }
        }
      stage("Docker push") {
          steps {
                sh "docker login -u onbehalfofme -p safujxUi7JJKxKm"
                sh "docker push onbehalfofme/attendance:server"
            }
        }
    }
}
