node {
    def app
    stage('Checkout') {
        git credentials: 'github', url: 'https://github.com/Onbehalfofme/automatic-check-attendance.git', branch: 'master'
    }
     stage('Build') {
        sh 'cd backend/ && ./gradlew build --no-daemon'
    }
    stage('Backend image build') {
        app = docker.build("onbehalfofme/attendance", "./backend")
    }
    stage('Push'){
        docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
        app.push("backend")
        }
    }
    stage('Deploy'){
        sh 'ssh project@134.209.227.130 "./deploy.sh"'
    }
}
