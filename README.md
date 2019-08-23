# Plan Generator
A microservice for computing loan repayment schedule.
# Description:
This service is developed to compute the monthly repayments throughout the lifetime of the loan.

# Tools used to develop:
* Programming language : Java 8  
* JDK : Open JDK-8
* Web framework : Spring-boot-2.1.7.RELEASE
* Build tool : Apache Maven 3.6.0
* Logging : Slf4j
* Help library to remove boiler plate code : Lombok 1.18
* For containerization : Docker

# How to build?
#### Pre-requisites: Maven 3.6+ and JDK 8 
Clone the project to the local repository and from the project root directory run the blow command.

    *   mvn clean install 

A jar file with all dependencies in it will be generated in the folder _ /target/annuity-plan-generator.jar_

# How to run?
In local env use the below command:

    *   java -jar -<path>/annuity-plan-generator.jar

When the service is started hit the url  _http://localhost:9999/_ you should see a welcome message.

# Docke commands
    *   To see all images : docker images
    *   To see all the running containers : docker ps -a
    *   To build image : docker build -d --tag laon-app:latest --rm=true .
    *   To run the image : docker run -p 8080:8080 loan-app:lates
    *   To login to docker : docker login
    *   To tag the image with repo : docker tag laon-app:lates <docker-username>/<your-repo>[:tag]
    *   To push the image to docker hub : docke push <docker-username>/<your-repo>[:tag]

# How to test?
This service exposes an POST end-point _http://localhost:9999/generate-plan_
It takes the below four _mandatory_ input parameters:

* loanAmount: total loan amount ("total principal amount")
* nominalRate: nominal interest rate
* duration: duration (number of instalments in months)
* startDate:  Date of Disbursement/Payout

You can use tools like Postman or curl commands to call the endpoint.

Example request values:

Headers:

       Content-Type:application/json

Request body:

       {
        	"loanAmount": "5000",
        	"nominalRate":"5",
        	"duration" : "24",
        	"startDate": "2018-01-01T00:00:01Z"
        }
      
