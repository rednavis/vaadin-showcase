FROM maven:3.6.3-jdk-8-slim as build
WORKDIR /usr/local/app
COPY pom.xml .
RUN mvn dependency:go-offline -Dsilent=true && mvn com.github.eirslett:frontend-maven-plugin:install-node-and-npm -DnodeVersion="v12.13.0"
COPY . .
RUN mvn -e -B package -DskipTests -Pproduction

FROM tomee:11-jre-8.0.0-M3-webprofile as tomee
RUN rm -Rf /usr/local/tomee/webapps/*
COPY tomcat-users.xml.overide /usr/local/tomee/conf/tomcat-users.xml
COPY server.xml.overide /usr/local/tomee/conf/server.xml
COPY tomee_run.sh /usr/local/
RUN chmod +x /usr/local/tomee_run.sh
COPY --from=build /usr/local/app/target/*.war /usr/local/tomee/webapps/app.war
ENV JAVA_OPTS="${JAVA_OPTS} -Xms256m -Xmx1024m -XX:+UseContainerSupport"
CMD /usr/local/tomee_run.sh


#FROM jboss/wildfly:18.0.1.Final
#COPY --from=build /usr/local/app/target/*.war /opt/jboss/wildfly/standalone/deployments/
#ENV JAVA_OPTS="${JAVA_OPTS} -Xms256m -Xmx1024m -XX:+UseContainerSupport"
#CMD /opt/jboss/wildfly/bin/standalone.sh -Djboss.socket.binding.port-offset=8 -Djava.net.preferIPv4Stack=true -b 0.0.0.0
 