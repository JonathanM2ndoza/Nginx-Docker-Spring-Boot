FROM nginx:1.18

MAINTAINER Jonathan S Mendoza

ENV TZ=America/Caracas
COPY ./conf/* /etc/nginx/

RUN cd /etc/nginx \
    && mkdir certs \
    && cd ./ \
    && chmod -R 755 certs

COPY ./certs/* /etc/nginx/certs/

RUN cd /home \
    && mkdir api-docs \
    && cd api-docs \
    && mkdir v1 \
    && cd ../ \
    && chmod -R 755 api-docs

COPY ./static/* /home/api-docs/v1/

EXPOSE 80

