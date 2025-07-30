# Spring Boot with Jenkins, SonarQube, and JaCoCo Integration

This project demonstrates how to integrate SonarQube code analysis and JaCoCo code coverage into a Jenkins pipeline for a Spring Boot application.

## Prerequisites

- Jenkins server with the following plugins installed:
  - SonarQube Scanner for Jenkins
  - Sonar Quality Gates Plugin
  - JaCoCo Plugin (for code coverage reports)
  - Pipeline
  - Maven Integration
  - Credentials Plugin
  - HTML Publisher Plugin (optional, for viewing HTML reports)
- SonarQube server (v8.0 or later recommended)
- Java 17 (as specified in the project)
- Maven 3.9.0

## Setup Instructions

### 1. SonarQube Server Configuration

1. Log in to your SonarQube server
2. Generate a user token:
   - Go to User → My Account → Security
   - Generate a new token
   - Copy the generated token (you'll need it for Jenkins)

### 2. Jenkins Configuration

1. **Install Required Plugins**:
   - Go to Jenkins → Manage Jenkins → Manage Plugins
   - Install the following plugins if not already installed:
     - SonarQube Scanner for Jenkins
     - SonarQube Quality Gates Plugin
     - JaCoCo Plugin (for code coverage reports)
     - Pipeline
     - Maven Integration
     - Credentials Plugin

2. **Configure SonarQube Server in Jenkins**:
   - Go to Jenkins → Manage Jenkins → Configure System
   - Find the "SonarQube servers" section
   - Click "Add SonarQube"
   - Enter a name (e.g., "SonarQube")
   - Enter your SonarQube server URL (e.g., http://sonarqube:9000)
   - Add authentication token:
     - Click "Add" → Jenkins
     - Kind: "Secret text"
     - Secret: [Paste your SonarQube user token]
     - ID: sonar-token (must match the credentials ID in Jenkinsfile)
     - Click "Add"
   - Select the credentials you just created
   - Save the configuration

3. **Configure SonarQube Scanner in Jenkins**:
   - Go to Jenkins → Manage Jenkins → Global Tool Configuration
   - Find "SonarQube Scanner" section
   - Click "Add SonarQube Scanner"
   - Name: SonarQubeScanner (must match the name in Jenkinsfile)
   - Select "Install automatically" or point to an existing installation
   - Save the configuration

### 3. JaCoCo Configuration

1. **Maven Configuration**:
   The project's `pom.xml` includes the JaCoCo Maven plugin configuration:
   ```xml
   <plugin>
       <groupId>org.jacoco</groupId>
       <artifactId>jacoco-maven-plugin</artifactId>
       <version>0.8.8</version>
       <executions>
           <execution>
               <goals>
                   <goal>prepare-agent</goal>
               </goals>
           </execution>
           <execution>
               <id>report</id>
               <phase>test</phase>
               <goals>
                   <goal>report</goal>
               </goals>
           </execution>
       </executions>
   </plugin>
   ```

2. **Jenkinsfile Configuration**:
   The pipeline is configured to:
   - Generate JaCoCo execution data during tests
   - Archive the JaCoCo coverage report
   - Publish the HTML report (if HTML Publisher Plugin is installed)
   - Pass coverage data to SonarQube

### 4. Project Configuration

The project is already configured with the necessary settings in the `Jenkinsfile`. Key configurations include:

- Maven and JDK versions
- SonarQube environment variables
- Analysis parameters (project key, source paths, etc.)
- JaCoCo report paths for test coverage

## Running the Pipeline

1. **Set Up a New Pipeline Job in Jenkins**:
   - Create a new "Pipeline" job
   - In the pipeline configuration:
     - Select "Pipeline script from SCM"
     - Choose your SCM (Git, etc.)
     - Enter your repository URL
     - Set the branch to build
     - Script path: Jenkinsfile (default)

2. **Run the Pipeline**:
   - Click "Build Now" to start the pipeline
   - The pipeline will execute the following stages:
     1. Initialize: Set up environment
     2. Build: Compile the application
     3. Code Analysis: Run SonarQube analysis
     4. Test: Execute unit tests
     5. Publish: Publish artifacts (if configured)

## Viewing Results

After the pipeline completes:

1. **In Jenkins**:
   - View the console output for detailed logs
   - Check the "SonarQube" link in the build sidebar (if configured)
   - View the JaCoCo coverage report (if HTML Publisher Plugin is installed):
     - Click on the build number
     - Select "JaCoCo" or "Coverage Report" from the left menu
   - Check the test coverage trend in the project's main page

2. **In SonarQube**:
   - Go to your SonarQube dashboard
   - Find your project (matching the project key in Jenkinsfile)
   - View code quality metrics, issues, and technical debt
   - Navigate to the "Measures" tab to see code coverage metrics
   - View the "Coverage" section for detailed line and branch coverage

## Customization

### SonarQube Customization
To customize the SonarQube analysis, modify the `withSonarQubeEnv` section in the `Jenkinsfile`. Common customizations include:

- Adding more analysis properties
- Including/excluding specific files or directories
- Setting quality gate conditions

### JaCoCo Customization

1. **Coverage Exclusions**:
   To exclude certain packages or classes from coverage:
   ```xml
   <configuration>
       <excludes>
           <exclude>**/config/**</exclude>
           <exclude>**/model/**/*Dto*</exclude>
       </excludes>
   </configuration>
   ```

2. **Coverage Thresholds**:
   Enforce minimum coverage requirements in your `pom.xml`:
   ```xml
   <execution>
       <id>check</id>
       <goals>
           <goal>check</goal>
       </goals>
       <configuration>
           <rules>
               <rule>
                   <element>BUNDLE</element>
                   <limits>
                       <limit>
                           <counter>LINE</counter>
                           <value>COVEREDRATIO</value>
                           <minimum>0.80</minimum>
                       </limit>
                   </limits>
               </rule>
           </rules>
       </configuration>
   </execution>
   ```

3. **HTML Report Customization**:
   Customize the HTML report output location and format in your `pom.xml`.

## Troubleshooting

### SonarQube Issues
- **Authentication Errors**: Verify the SonarQube token in Jenkins credentials
- **Scanner Not Found**: Check the SonarQube Scanner configuration in Jenkins
- **Analysis Fails**: Review the Jenkins console output for detailed error messages
- **Missing Coverage**: Ensure tests are running before the SonarQube analysis

### JaCoCo Issues
- **No Coverage Data**:
  - Ensure tests are running with the JaCoCo agent
  - Check that the `prepare-agent` goal is executed before tests
  - Verify the execution data file path in the Jenkinsfile matches the Maven configuration

- **Low Coverage**:
  - Review the JaCoCo report to identify untested code
  - Consider adding more test cases for uncovered code paths
  - If certain code should be excluded from coverage, update the exclusions in `pom.xml`

- **HTML Report Not Showing**:
  - Ensure the HTML Publisher Plugin is installed
  - Check the report path in the Jenkinsfile matches the actual report location
  - Verify the build has the necessary permissions to publish the report

## References

### SonarQube
- [SonarQube Documentation](https://docs.sonarqube.org/)
- [Jenkins SonarQube Plugin](https://docs.sonarqube.org/analysis/scan/sonarscanner-for-jenkins/)
- [Maven SonarQube Plugin](https://docs.sonarqube.org/analysis/scan/sonarscanner-for-maven/)

### JaCoCo
- [JaCoCo Maven Plugin](https://www.jacoco.org/jacoco/trunk/doc/maven.html)
- [JaCoCo Jenkins Plugin](https://plugins.jenkins.io/jacoco/)
- [Code Coverage Best Practices](https://www.jacoco.org/jacoco/trunk/doc/)

### Integration
- [SonarQube with JaCoCo](https://docs.sonarqube.org/analysis/coverage/)
- [Jenkins Pipeline with JaCoCo](https://plugins.jenkins.io/jacoco/)
