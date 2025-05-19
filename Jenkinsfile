pipeline {
    agent {
        node {
            label 'zextras-v1'
        }
    }
    environment {
        JAVA_OPTS = '-Dfile.encoding=UTF8'
        LC_ALL = 'C.UTF-8'
        jenkins_build = 'true'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '25'))
        timeout(time: 2, unit: 'HOURS')
        skipDefaultCheckout()
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                container('jdk-17') {
                    withCredentials([file(credentialsId: 'artifactory-jenkins-gradle-properties', variable: 'CREDENTIALS')]) {
                        sh '''
                            cat <<EOF > build.properties
                            debug=0
                            is-production=1
                            carbonio.buildinfo.version=22.3.0_ZEXTRAS_202203
                            EOF
                           '''
                        sh "cat ${CREDENTIALS} | sed -E 's#\\\\#\\\\\\\\#g' >> build.properties"
                        sh '''
                            apt update && apt install -y ant
                            ANT_RESPECT_JAVA_HOME=true JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64/ ant \
                                 -propertyfile build.properties \
                                 jar
                            '''
                    }
                }
            }
        }

        stage('Publish to maven') {
            when {
                buildingTag()
            }
            steps {
                container('jdk-17') {
                    withCredentials([file(credentialsId: 'artifactory-jenkins-gradle-properties', variable: 'CREDENTIALS')]) {
                        sh '''
                            cat <<EOF > build.properties
                            debug=0
                            is-production=1
                            carbonio.buildinfo.version=22.3.0_ZEXTRAS_202203
                            EOF
                           '''
                        sh "cat ${CREDENTIALS} | sed -E 's#\\\\#\\\\\\\\#g' >> build.properties"
                        sh '''
                            ANT_RESPECT_JAVA_HOME=true JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64/ ant \
                                 -propertyfile build.properties \
                                 publish-maven-all
                            '''
                    }
                }
            }
        }
    }
}

