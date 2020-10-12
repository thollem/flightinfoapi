# flightinfoapi

flightinfoapi a Java Springboot Demo REST Api based on the Flight Aware site

## General Info ====

  1) clone the Springboot Java8 REST project and go to the root folder containing the mvn pom and run:
  2) mvn clean install -Dmaven.test.skip=true
  3) mvn spring-boot:run

  The server runs on port 8085, and try the next url: http://localhost:8085/flightInfoTemplate?ident=EC-MYT&howMany=15&offset=0
  
  There are three Spring Profiles: dev, prod and test
  
  The application profile is set to test, running on an H2 database
  
  In case you'd like to go for another profile (dev or prod) you have to install a DB. See the src\main\resources\application.properties and the src\main\java\com\artsgard\hotelbookingbackend\DBConfig class for futher configuration.
  
 ## About the Flight Info Application
