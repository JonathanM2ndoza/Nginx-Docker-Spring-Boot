FROM nginx:1.18

MAINTAINER Jonathan S Mendoza

ENV TZ=America/Caracas
COPY ./conf/* /etc/nginx/

RUN cd /etc/nginx \
    && mkdir certs \
    && cd ./ \
    && chmod -R 755 certs

COPY ./certs/* /etc/nginx/certs/

RUN apt-get update \
    && apt-get install -y certbot

RUN apt-get update \
    && apt-get install -y python-certbot-nginx

EXPOSE 80

