# Microservices Architecture Example
Microservicio con Spring Boot y MySQL para la gestión de clientes, cuentas y movimientos bancarios.

Para deployar todos los contenedores en docker-compose se debe realizar ejecutar los siguientes comandos en la terminal (la ruta puede variar):

```
cd C:\Users\user\IdeaProjects\microservices-architecture-example
mvn clean package -DskipTests
docker-compose up -d
```

Para acceder de manera local se incluye en el navegador la siguiente URL.

```
http://localhost:8081/swagger-ui/index.html
```


Para detener y eliminar los contenedores se debe ejecutar los siguientes comandos.

```
cd C:\Users\user\IdeaProjects\microservices-architecture-example
docker-compose down
docker-compose rm
```


## NOTA: 
Tener previamente instalado Maven, Docker y Docker Desktop para la ejecución de la misma.
