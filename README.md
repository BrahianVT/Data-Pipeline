# Data Pipeline Example
First of all What is a Data Pipeline?  
A Data pipeline  is a series of data processing steps. Then there are a series of steps in which each step  
delivers an output that is the input to the next step.  

Data pipelines consist of three key elements:
* A Source
* Processing Steps
* A Destination

Complex data pipelines include another tasks which involke a lot of systems, technologies and platforms such as Apache Hive and products  
of Microsoft or Amazon, but for our example just I will cover the three steps above, a source(data from a source in internet), the processing steps to  
clean the data and get or create new data from it data and the destination a database.  

## Data PipeLine Solution
![Alt text](img/diagrama.jpeg?raw=true "Data Pipeline Solution")

## I created two services in the project:
* ConsumeApiFillDataBase
* RestApi


The first service  called **ConsumeApiFillDataBase** will persistence to a MySql database from this page:  
[Source](https://datos.cdmx.gob.mx/dataset/prueba_fetchdata_metrobus) 
In this source the data is updated each hour, so this data service will fetch new data every hour.  

Here we need to do some process steps before save to the data base,  we need filter the revelant information for me (check the table below)  
and also I need to find a relevant data that are not present in the result , this field is called county, in some register the county is present, but not in all.    

In order to find the county I need to call this API from OpenStreetMap, also Here we can use google maps but after a amount of calls google will charge you some money :(  

To call this service we just need the latitude and longitude.  
This is the site https://nominatim.openstreetmap.org and the documentation is here [Api Documentation](https://nominatim.org/release-docs/develop/api/Overview/)  
Here I used the service called  [Reverse ](https://nominatim.org/release-docs/develop/api/Reverse/)   

## Data base
Basically This is the table:  

![Alt text](img/table.PNG?raw=true "MySql Table")

**Technologies**
* Java 8 and Maven
* Intellij Idea, Windows 10 Home
* Lombok Intellij Idea plugin
* Docker and Docker Compose(docker tool box) (https://docs.docker.com/toolbox/toolbox_install_windows/) for Windows 10 home
* [Spring Initialzr](https://start.spring.io/)  Used in the project calld RestApi.
* MySql and H2 as databases
* Java 8, Spring Boot 2, Maven, Spring Data, Spring Data, Lombok, JUnit, Mockito, Hamcrest, Docker Compose , Slf4j y Swagger

### Settings

First clone this repository and from the terminal execute the following command, make sure you installed docker-compose before.  
Execute this command to run the MySQL service (this in the root folder):  

```
docker-compose up
```

In the terminal go to the folder **ConsumeApiFillDataBase** and execute the command:  
```
docker build --tag=api-store-bd:1.0 --rm=true .
```

There is another file here called docker-compose.yml, execute this command to run the service.  
```
docker-compose up
```

In the terminal go to the folder **RestApi** and execute:  
```
docker build --tag=api-rest:1.0 --rm=true .
```
And  

```
docker-compose up
```


These services are java projects, so if you do not want to use docker containers just:  
Go to the folder **Target** y ejecute the command:  

```
java -jar "name".jar
```

The documentation of the RestApi services is swagger: 


### Swagger
Here are the endPoints: http://localhost:8080/swagger-ui.html

#### EndPoint
/api/metrobusInfo/findAllMetroBus/{pageNo}/{pageSize}  
Get all the records from the table     
Example http://localhost:8080/api/metrobusInfo/findAllMetroBus/1/20  Get  the firstprimero 20 registers from the page 1  


​/api​/metrobusInfo​/findByLonAndLat​/{longitude}​/{latitude}  
Get all the records from the database based on the longitude and latitude  
Example http://localhost:8080/api/metrobusInfo/findByLonAndLat/-99.18779754638672/19.31749916076660  

/api/metrobusInfo/findByCounty/{county}  
Get all the records for a specific county
Example http://localhost:8080/api/metrobusInfo/findByCounty/Iztapalapa


​/api​/metrobusInfo​/findByIdVehicle​/{idVehicle}
Get all the records for a specific vehicle
http://localhost:8080/api/metrobusInfo/findByIdVehicle/1078

/api/metrobusInfo/findAllCounty
Get all the the counties in the database  
http://localhost:8080/api/metrobusInfo/findAllCounty  

Remember to change the uri depending on whether you are using docker or not.
