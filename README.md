# Nginx-Docker-Spring-Boot

This example has a basic configuration of NGINX, Tomcat and PostgreSQL. It is necessary to activate HTTPS / SSL security in Tomcat and NGINX for a production environment.

### Configure the values of file Dockerfile for PostgreSQL

*Path: /docker/postgres/*

```shell
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=root.jmtizure.k201
ENV POSTGRES_DB=wallet-zelle
```
![Screenshot](prtsc/Deploy-Wallet-3.png)

### Copy the .sql scripts that will be automatically executed inside the Docker container

*Path: /docker/postgres/scripts*

![Screenshot](prtsc/Deploy-Wallet-2.png)

### Configure the values of file application.properties for Spring Boot and Tomcat

They are the same data added in the Database Dockerfile.

*Path: /configuration/src/main/resources/*

```shell
spring.datasource.url=jdbc:postgresql://db-wallet:5432/wallet-zelle
spring.datasource.username=postgres
spring.datasource.password=root.jmtizure.k201
```

![Screenshot](prtsc/Deploy-Wallet-4.png)

### Compile Project

```shell
jmendoza@jmendoza-ThinkPad-T420:~/IdeaProjects/JonathanM2ndoza/Nginx-Docker-Spring-Boot$ ls
api-war  application  common  configuration  docker  domain  infrastructure  pom.xml  README.md  security

jmendoza@jmendoza-ThinkPad-T420:~/IdeaProjects/JonathanM2ndoza/Nginx-Docker-Spring-Boot$ mvn clean install
```

![Screenshot](prtsc/Deploy-Wallet-1.png)

*The War file is automatically moved to the docker folder (/docker/tomcat/war/) to form the container image.*

```shell
jmendoza@jmendoza-ThinkPad-T420:~/IdeaProjects/JonathanM2ndoza/Nginx-Docker-Spring-Boot/docker/tomcat/war$ ls
api-war-1.0.war
```

*Note: The current name of the WAR file is api-war-1.0.war, if you change it then you should update it in the files Dockerfile and server.xml*

### Execute docker-compose 

```shell
jmendoza@jmendoza-ThinkPad-T420:~/IdeaProjects/JonathanM2ndoza/Nginx-Docker-Spring-Boot/docker$ docker-compose up -d --build

Successfully built 329df9b3541e
Successfully tagged db-wallet:1.0

Successfully built bd317f70c861
Successfully tagged api-wallet:1.0
Creating docker_db-wallet_1 ... done
Creating docker_api-wallet_1 ... done

```


![Screenshot](prtsc/Deploy-Wallet-6.png)

## NGINX

## System Architecture

soon
