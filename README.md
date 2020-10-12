# flightinfoapi

flightinfoapi a Java Springboot Demo REST Api based on the Flight Aware site

## General Info ====

  1) clone the Springboot Java8 REST project and go to the root folder containing the mvn pom and run:
  2) mvn clean install -Dmaven.test.skip=true
  3) mvn spring-boot:run

  The server runs on port 8085, and try the next url: http://localhost:8085/flightInfoTemplate?ident=EC-MYT&howMany=15&offset=0
  
  You´ll find three Spring Profiles: dev, prod and test
  
  The application profile is set to test, running on an H2 database, so there is no need of installing a DB.
  
  In case you'd like to go for another profile (dev or prod) you have to install a DB. See the src\main\resources\application.properties and the src\main\java\com\artsgard\hotelbookingbackend\DBConfig class for futher configuration.
  
 ## About the Flight Info Application
 
 The idea of the demo api is the following:
 
     1) You enter a large chunk of flight data bassed on the aiplane`s tail-number EC-MYT by running the next url: http://localhost:8085/findFlightInfoByTailnumber/EC-MYT The data will be stored into two tables called flight_info and airport_display.
     
     2)
 
