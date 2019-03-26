node {
    def app
    stage('Checkout') {
        git url: 'https://github.com/Onbehalfofme/automatic-check-attendance.git', branch: 'master'
    }
     stage('Build') {
        sh 'cd backend/ && ./gradlew build --no-daemon && cd ..'
    }
    stage('Docker image build') {
        app = docker.build("onbehalfofme/attendance")
    }
    stage("Push"){
        docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
        app.push("${env.BUILD_NUMBER}")
        app.push("latest")
        }
    }
}
