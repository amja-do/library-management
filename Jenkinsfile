pipeline {
    agent any
    environment {
        MAVEN_HOME = tool 'Maven'
        SONAR_PROJECT_KEY = 'library-management'
		SONAR_SCANNER_HOME = tool 'SonarQubeScanner'
    }
    stages {
        stage('checkout') {
            steps {
                withCredentials([string(credentialsId: 'github-pat', variable: 'GITHUB_PAT')]) {
                script {
                    if (fileExists('library-management')) {
                        dir('library-management') {
                            sh "git reset --hard" 
                            sh "git clean -fd" 
                            sh "git pull origin main"
                        }
                    } else {
                        sh "git clone https://${GITHUB_PAT}@github.com/amja-do/library-management.git"
                    }
                }
                }
            }
        }
        stage('Build') {
            steps {
                sh '${MAVEN_HOME}/bin/mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh '${MAVEN_HOME}/bin/mvn test'
            }
        }
        stage('Quality Analysis') {
            steps {
				withCredentials([string(credentialsId: 'SonarQube-library-management-token', variable: 'SONAR_TOKEN')]) {
				   
					withSonarQubeEnv('SonarQube') {
						sh """
                             mvn sonar:sonar \
                            -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                            -Dsonar.login=${SONAR_TOKEN}
                    	"""
					}	
				}
			}
        }
        stage('Deploy') {
            steps {
                echo 'Déploiement simulé réussi'
            }
        }
    }
    post {
        success {
            mail to: 'amri.amjado@gmail.com',
                subject: 'Build Success',
                body: 'Le build a été complété avec succès.'
        }
        failure {
            mail to: 'amri.amjado@gmail.com',
                subject: 'Build Failed',
                body: 'Le build a échoué.'
        }
    }
}
