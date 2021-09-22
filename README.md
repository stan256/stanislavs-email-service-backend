Backlog:
1. Message Queue: a layer between the Email Controller and Email service, which will be presented with some MQ like RabbitMQ, Kafka, etc.
2. To hide the API client id's & client secret in something like Vault to not have them hardcoded.
3. Using html template engine for generating (Velocity, Freemarker, etc.)
4. Adding the support for BCC in emails
5. Using the own domain email (for the support of DKIM, SPF)
6. Tests for the frontend
7. Providing of the more detailed message for the frontend in case of error

Providers:
Mailjet & Sendgrid were used for email sendings

Experience with used technologies:
Java 8+: ~6years
Spring Framework 4+: ~5 years
Angular 8+: ~3 years (with pauses)
Docker & Heroku: ~3 years

Link for the testing (frontend): https://email-service-frontend.herokuapp.com/email-constructor

Local run:
Backend could be started as the Docker container, app contains Dockerfile with predefined instructions.
Please run "docker build -t email-service ." for the building of image and execute "docker run -p 8080:8080 email-service" for running the container.
Frontend could be started with the execution of command "ng start" (angular-cli is required).

Scalability:
The application can be easly scaled due to stateless organization.
For the future, I would like to present storing requests for email sendings in database or MQ, which also may be implemented with the readiness to work with well horizontal scalability.

Security:
Backend application accepts all requests (CORS settings) for the endpoint which is serving for sending the emails.
All other endpoints (healthcheck) do not support cross-origin requests 
