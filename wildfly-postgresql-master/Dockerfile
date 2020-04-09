FROM jboss/wildfly:18.0.0.Final
MAINTAINER Antonin Stoklasek

ENV WILDFLY_HOME /opt/jboss/wildfly
ENV DEPLOY_DIR ${WILDFLY_HOME}/standalone/deployments/

USER root

USER jboss

ENV DATASOURCE_NAME Postgres
ENV DATASOURCE_JNDI java:jboss/datasources/Postgres

ENV DB_HOST postgres
ENV DB_PORT 5432
ENV DB_USER postgres
ENV DB_PASS 15sl02tt
ENV DB_NAME postgres

# create temporary deployment dir, because wars can deploy after the datasource is created
ENV postgres_module_dir=/opt/jboss/wildfly/modules/org/postgresql/main
RUN mkdir -p ${postgres_module_dir}
ADD module.xml ${postgres_module_dir}
WORKDIR ${postgres_module_dir}
ADD postgresql-42.2.5.jar ${postgres_module_dir}

RUN mkdir /tmp/deploments
ENV DEPLOY_DIR /tmp/deploments

RUN mkdir /tmp/jboss-cli
ENV CLI_DIR /tmp/jboss-cli

ENV config_dir=/opt/jboss/wildfly/standalone/configuration/
ADD standalone.xml ${config_dir}

COPY startWithPostgres.sh $WILDFLY_HOME/bin

USER root
RUN chown jboss:jboss $WILDFLY_HOME/bin/startWithPostgres.sh
RUN chmod 755 $WILDFLY_HOME/bin/startWithPostgres.sh
USER jboss

COPY postgresql-42.2.5.jar /tmp

#Package and add war in current folder before docker build
COPY web-1.0-SNAPSHOT.war /tmp/deploments

ENTRYPOINT $WILDFLY_HOME/bin/startWithPostgres.sh
