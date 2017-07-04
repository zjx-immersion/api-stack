stage 'Checkout project'
node {
    git url: 'https://github.com/zjx-immersion/api-stack.git'
}

stage 'Check env'
node {
    sh "java -version"
    sh "docker version"
}

stage 'Build and Unit test'
node {
    sh "./gradlew clean build"
    dir("build") {
        sh "ls -a"
    }
    junit 'build/test-results/test/TEST-*.xml'
    stash excludes: 'src/', includes: '**', name: 'source'
}

stage 'API test'
node {
    sh "./gradlew apiTest"
    dir("build") {
        sh "ls -a"
    }
}

stage 'package'
node {
    sh "./gradlew bootRepackage"
    sh "ls -a"

    archive includes: "build/libs/*.jar"
    archive includes: "build/*"

}

stage 'Test Report'
node {
    unstash 'source'
    dir("build") {
        sh "ls -a"
    }
    publishHTML(target: [
            allowMissing         : false,
            alwaysLinkToLastBuild: false,
            keepAll              : true,
            reportDir            : 'build/reports/jacoco/html',
            reportFiles          : 'index.html',
            reportName           : "RCov Report"
    ])
}

stage 'Artifact'
node {
    step([$class: 'ArtifactArchiver', artifacts: '**/build/libs/*.jar', fingerprint: true])
}


stage 'Approve, go production'
node {
    try {
        def url = 'http://localhost:90011/info'
        input message: "Does staging at $url look good? ", ok: "Deploy to production"
    } finally {

        sh "echo 'Ready to prepare the ENV and Deployment'"
    }
}

stage('docker clear')
node {
    script {
        sh 'chmod +x ./src/main/docker/clear.sh'
        POM_VERSION = sh(script: "./src/main/docker/clear.sh", returnStdout: true)
        echo "${POM_VERSION}"
    }
    sh "./gradlew buildDocker"
}


stage 'Deploy'
node {
    sh "echo 'Run API Server in Container'"
    sh "docker run --name=api-server -d -p 5000:8082 zhongjx/api-stack "
}

post {
    always {
        deleteDir()
    }
}


def archiveUnitTestResults() {
    step([$class: "JUnitResultArchiver", testResults: "build/**/TEST-*.xml"])
}

def archiveCheckstyleResults() {
    step([$class         : "CheckStylePublisher",
          canComputeNew  : false,
          defaultEncoding: "",
          healthy        : "",
          pattern        : "build/reports/checkstyle/main.xml",
          unHealthy      : ""])
}
