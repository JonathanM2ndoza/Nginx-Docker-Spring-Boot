FROM postgres:12.3
MAINTAINER Jonathan S Mendoza

ENV TZ=America/Caracas

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=root.jmtizure.k201
ENV POSTGRES_DB=wallet-zelle

COPY ./scripts/* /docker-entrypoint-initdb.d/
RUN chmod -R 755 /docker-entrypoint-initdb.d/*
