<img src="./shorten.png" alt="Banner" style="width: 40%;" />

# URL Shortening Service
**shorten-url** is a reference solution to https://roadmap.sh/projects/url-shortening-service. 

The **shorten-url** development project is built using the Java Spring Boot framework. In particular, the development project utilizes the Spring Web and Spring Data JDBC starters for their controller and repository solution accordingly. The development project also persists its data in an in-memory H2 database. In addition to the core business logic, the **shorten-url** development project has comprehensive exception handling and modular testing

The **shorten-url** project in production is run fully managed by AWS App Runner via a Docker image of the **shorten-url** project that has been uploaded to AWS ECR. The production version of **shorten-url** also persists its data in a managed instance of AWS RDS for MySQL

You can read my brief reflections on [https://dev.to/jason_oh/url-shortening-service-reflections](https://dev.to/jason_oh_242b4e371630fdac/url-shortening-service-reflections-44fj)

## Accessing the Production Project Service
For a teaser of the service, visit the following "shortened" url, which redirects to a google query for "dogs"
--> https://tu7uf3drug.us-east-2.awsapprunner.com/1cpf0c

Access the Postman collection for the API via [https://documenter.getpostman.com/view/shorten-url](https://documenter.getpostman.com/view/44168162/2sB2j68q3V#562662ea-c24c-4969-be0e-e3cfeffebc7f).  
The collection provides ``curl`` requests that you can use to interact with the API from a cli tool.  
However, it is recommended that you import the collection to Postman and interact with the API from there

Either way, you must replace the {your-query-word} and {shortcode} parameters in the HTTP requests

Note: please read the Troubleshooting note on the collection regarding the use of ``curl`` requests

## Running the Development Project Locally
1. **Clone the Repository**
``` bash
git clone https://github.com/Jason0h/shorten-url.git  
cd shorten-url
```
### Option A: Running From Source  
2. **Run the Application**
``` bash
./mvnw spring-boot:run
```
Prerequisite: Java 17+ must be available on your system's ``PATH``. You can check installation with ``java --version``
### Option B: Running With Docker
2. **Build the Docker Image**
``` bash
docker build -f docker/dev/Dockerfile -t shorten-url .
```
3. **Run a Docker Container**
``` bash
docker run -p 8080:8080 --name shorten-url shorten-url
```
Prerequisite: Docker must be installed on your system (install Docker Desktop if this is not the case)

Troubleshooting: Note that an existing host process listening on port 8080 can prevent the app from running.  
In that case, kill the existing process before running the aforementioned instructions

You will be able to interact with the local development project on http://localhost:8080

### Running Tests From Source
``` bash
./mvnw test
```
