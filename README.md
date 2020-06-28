# Nginx-Docker-Spring-Boot

This example has a basic configuration of NGINX, Tomcat and PostgreSQL. It is necessary to activate HTTPS / SSL security in Tomcat and NGINX for a production environment.

### Configure the values of file database_env for PostgreSQL

*Path: /docker/postgres/*

```shell
POSTGRES_USER=postgres
POSTGRES_PASSWORD=root.jmtizure.k201
POSTGRES_DB=wallet-zelle
```

### Configure the values of file application.properties for Spring Boot and Tomcat

*Path: /configuration/src/main/resources/*

```shell
spring.datasource.url=jdbc:postgresql://db-wallet:5432/wallet-zelle
spring.datasource.username=postgres
spring.datasource.password=root.jmtizure.k201
```
### Compile Project

```shell
jmendoza@jmendoza-ThinkPad-T420:~/IdeaProjects/JonathanM2ndoza/Nginx-Docker-Spring-Boot$ ls
api-war  application  common  configuration  docker  domain  infrastructure  pom.xml  README.md  security

jmendoza@jmendoza-ThinkPad-T420:~/IdeaProjects/JonathanM2ndoza/Nginx-Docker-Spring-Boot$ mvn clean install
```

![Screenshot](prtsc/Deploy-Wallet-1.png)

The War file is automatically moved to the docker folder (/docker/tomcat/war/) to form the container image.

```shell
jmendoza@jmendoza-ThinkPad-T420:~/IdeaProjects/JonathanM2ndoza/Nginx-Docker-Spring-Boot/docker/tomcat/war$ ls
api-war-1.0.war
```

### Execute docker-compose 

```shell
jmendoza@jmendoza-ThinkPad-T420:~/IdeaProjects/JonathanM2ndoza/Nginx-Docker-Spring-Boot/docker$ docker-compose up -d --build
```

## Execute the following scripts in the public schema. Database: wallet-zelle

soon

## System Architecture

soon
