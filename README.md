Develop a survey management system using microservices architecture using rest API. 

Surveys are question and Answers (Support options and free form response).

Functional requirements

Administrator Use Case The system should allow the administrator to create (setup) surveys. Support for different versions of same Survey.

Customer Use Case (can be valid login as well as anonymous) The system should allow users to access and answers the survey Questionaire. 
The system should allow users to resume surveys, where they have left before (Not for anonymous users)
Implement Roles based Autentication for the Services.

Few non-functional requirements are

• There should be sufficient test coverage (unit / integration tests) and code quality/security checks. 
 • Service(s) should be observable, it should have sufficient logs and metrics 
 • Service(s) should be containerized and deploy-able to Kubernetes 
 • Architecture diagram and sequence diagram.
 . Create a single page application for the basic UI for the above Customer and Administrator Use cases.
 
 Nice to Have
 
  • Automate all stages within a CI/CD pipeline 
  . Kubernetes artifacts for the micro-services.
  
Basic UI to demonstrate the flow
Required Artifacts
Source code Unit /Integration tests Docker/K8 files JenkinsPipeline scripts
