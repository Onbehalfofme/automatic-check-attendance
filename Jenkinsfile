node {
    def app
    stage('Checkout') {
        git credentials: 'github', url: 'https://github.com/Onbehalfofme/automatic-check-attendance.git', branch: 'master'
    }
     stage('Build') {
        sh 'cd backend/ && ./gradlew build --no-daemon'
    }
    stage('Backend image build') {
        backend = docker.build("onbehalfofme/attendance", "./backend")
    }
    stage('Push backend image'){
        docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
        backend.push("backend")
        }
    }
     stage('Backend image build') {
            frontend = docker.build("onbehalfofme/attendance", "./frontend")
        }
        stage('Push backend image'){
            docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
            backend.push("frontend")
            }
        }
    stage('Deploy'){
        sh 'ssh project@134.209.227.130 "./deploy.sh"'
    }
}
