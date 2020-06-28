FROM tomcat:9.0.36-jdk8-openjdk
MAINTAINER Jonathan S Mendoza

ENV TZ=America/Caracas

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN ["rm", "-fr", "/usr/local/tomcat/webapps/api-war-1.0.war"]

COPY ./war/api-war-1.0.war /usr/local/tomcat/webapps/api-war-1.0.war

CMD ["catalina.sh", "run"]
