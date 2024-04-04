# gcloud-emulators-guide


## Setup Instructions

1. Clone the repository.
2. Navigate to the project directory.
3. Run `docker-compose up` to start the Docker services.
4. Build and run the Spring Boot application.

**Note**: When running in debug mode (`DEBUG: true`), ensure that the `CloudTaskService.createCustomTask` method is configured to send the task to the correct endpoint (`http://host.docker.internal:8080/api/hello`). This will trigger the `TestController` and you should see the response from the `GET` or `POST` endpoint based on the HTTP method specified.
