# Data Pipeline Example
Esta es la prueba para vacante de Back-End

## Diagrama de Solución
![Alt text](img/diagrama.jpeg?raw=true "Diagrama de Solución Propuesto")

## Aqui se realizó como solución de 2 Proyectos:
* ConsumeApiFillDataBase
* RestApi

El Primero persistirá en base de datos de MySql registros de la pagina de datos abiertos de la CDMX:
[Pagina](https://datos.cdmx.gob.mx/explore/dataset/prueba_fetchdata_metrobus/api/) 
Esta se almazenará en un contenedor de Docker se configuró para que cada hora cheque en la pagina por mas datos porque 
cada hora se actualiza en la pagina.

Para encontrar la delegación se consulta este servicio del API de OpenStreetMap:
https://nominatim.openstreetmap.org

## Base de datos
Basicamente se almacena en esta tabla:  

![Alt text](img/table.PNG?raw=true "Tabla MySql")

**Tecnologias Utilizadas**
* Java 8 y Maven
* Intellij Idea, Windows 10 Home
* Lombok Intellij Idea plugin
* Docker y  Docker Compose(docker tool box) (https://docs.docker.com/toolbox/toolbox_install_windows/)  en mi caso Windows 10 home
* [Spring Initialzr](https://start.spring.io/)  Se uso para el proyecto RestApi
* MySql and H2 as databases
* Java 8, Spring Boot 2, Maven, Spring Data, Spring Data, Lombok, JUnit, Mockito, Hamcrest, Docker Compose , Slf4j y Swagger

### Configuración e Instalación

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
