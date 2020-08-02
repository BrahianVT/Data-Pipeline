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
[Source](https://datos.cdmx.gob.mx/explore/dataset/prueba_fetchdata_metrobus/api/) 
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

Primero descarga el proyecto y ve a la carpeta padre desde CMD, antes de eso aseguresé de tener docker-compose instalado.
Ejecute el comando para levantar el servicio de MySQL:

```
docker-compose up
```

Segundo ve al proyecto desde CMD **ConsumeApiFillDataBase** y ejecutar primero:
```
docker build --tag=api-store-bd:1.0 --rm=true .
```

Creamos en la misma ruta otro archivo llamado docker-compose.yml, esto iniciará el servicio de este proyecto y se ejecutara el proyecto
que ira a guardar a la base de datos.  
```
docker-compose up
```

Segundo ve al proyecto desde CMD **RestApi** y ejecutar primero:  
```
docker build --tag=api-rest:1.0 --rm=true .
```
Y despues

```
docker-compose up
```
Este proyecto levantará El Rest API.


Al estar realizado en java se pueden ejecutar los archivos .jar de los proyectos por si no quiere crear los docker containers:
Solo entre ala carpeta **Target** y ejecute los jar mediante este comando:

```
java -jar "name".jar
```

Los Rest END-POINT son estos vea la documentacion en swagger: 


### Swagger
Si el proyecto levantó correctamente puede ver los endPoints aqui : http://localhost:8080/swagger-ui.html

#### EndPoint
/api/metrobusInfo/findAllMetroBus/{pageNo}/{pageSize}  
Obtiene todos los registros de la tabla metrobus_info   
Ejemplo http://localhost:8080/api/metrobusInfo/findAllMetroBus/1/20  obtiene primero 20 registros, de la pagina 1  


​/api​/metrobusInfo​/findByLonAndLat​/{longitude}​/{latitude}  
Obtiene todos los registros en base a la longitud y latitud  
Ejemplo http://localhost:8080/api/metrobusInfo/findByLonAndLat/-99.18779754638672/19.31749916076660  

/api/metrobusInfo/findByCounty/{county}  
Obtiene todos los registros de una delegación  
Ejemplo http://localhost:8080/api/metrobusInfo/findByCounty/Iztapalapa


​/api​/metrobusInfo​/findByIdVehicle​/{idVehicle}
Obtiene registros en base al Id de un vehiculo
http://localhost:8080/api/metrobusInfo/findByIdVehicle/1078

/api/metrobusInfo/findAllCounty
Obtiene todas las delegaciones que estan en DB  
http://localhost:8080/api/metrobusInfo/findAllCounty  

Recordatorio si no uso docker puede consultar estos servicio desde aqui de otra manera acceda en base mediante la dirección de docker  
