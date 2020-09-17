FROM tomcat:9.0.38-jdk8-openjdk
MAINTAINER Jonathan S Mendoza

ENV TZ=America/Caracas
ENV JPDA_ADDRESS="8000"
ENV JPDA_TRANSPORT="dt_socket"
ENV JAVA_OPTS="-Xms1024m -Xmx2048m -XX:PermSize=512m -XX:MaxPermSize=1024m"

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN ["rm", "-fr", "/usr/local/tomcat/webapps/api-war-1.0.war"]

#RUN apt-get update \
#    && apt-get install -y rng-tools \
#    && echo "HRNGDEVICE=/dev/urandom" >> /etc/default/rng-tools \
#    && /etc/init.d/rng-tools start

COPY ./war/api-war-1.0.war /usr/local/tomcat/webapps/api-war-1.0.war
COPY ./conf/* /usr/local/tomcat/conf/

RUN cd /usr/local/tomcat \
    && mkdir data \
    && cd data \
    && mkdir customer_documents \
    && cd ../ \
    && chmod -R 755 data

ENTRYPOINT ["catalina.sh", "jpda", "run"]
