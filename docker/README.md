## Run Api Wallet Zelle 

1. *Compile the project to generate the WAR*

```shell
jmendoza@jmendoza-ThinkPad-T420:~/IdeaProjects/JonathanM2ndoza/Api-Wallet-Zelle-Prod$ ls
api-war  application  common  configuration  docker  domain  infrastructure  pom.xml  security

jmendoza@jmendoza-ThinkPad-T420:~/IdeaProjects/JonathanM2ndoza/Api-Wallet-Zelle-Prod$ mvn clean install 

[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ api-war ---
[INFO] Installing ./docker/tomcat/war/api-war-1.0.war to /home/jmendoza/.m2/repository/com/jmendoza/wallet/api-war/1.0/api-war-1.0.war
[INFO] Installing /home/jmendoza/IdeaProjects/JonathanM2ndoza/Api-Wallet-Zelle-Prod/api-war/pom.xml to /home/jmendoza/.m2/repository/com/jmendoza/wallet/api-war/1.0/api-war-1.0.pom
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for api-wallet-zelle 1.0:
[INFO] 
[INFO] api-wallet-zelle ................................... SUCCESS [  1.428 s]
[INFO] common ............................................. SUCCESS [  8.142 s]
[INFO] domain ............................................. SUCCESS [  1.954 s]
[INFO] security ........................................... SUCCESS [  1.642 s]
[INFO] application ........................................ SUCCESS [  1.338 s]
[INFO] infrastructure ..................................... SUCCESS [  2.644 s]
[INFO] configuration ...................................... SUCCESS [  1.266 s]
[INFO] api-war ............................................ SUCCESS [  4.116 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  24.089 s
[INFO] Finished at: 2020-06-25T12:32:59-04:00
[INFO] ------------------------------------------------------------------------
jmendoza@jmendoza-ThinkPad-T420:~/IdeaProjects/JonathanM2ndoza/Api-Wallet-Zelle-Prod$ 


```
**The WAR file is moved to the directory: /Api-Wallet-Zelle-Prod/docker/tomcat/war**

2. *Assign the execute permissions to file docker-compose.yml*

```shell
jmendoza@jmendoza-ThinkPad-T420:~/IdeaProjects/JonathanM2ndoza/Api-Wallet-Zelle-Prod/docker$ sudo chmod +x docker-compose.yml
```

2. Execute Docker Compose

```shell
jmendoza@jmendoza-ThinkPad-T420:~/IdeaProjects/JonathanM2ndoza/Api-Wallet-Zelle-Prod/docker$ docker-compose up -d --build

```
