FROM maven:3.6.3-jdk-8-slim as build
WORKDIR /usr/local/app
COPY pom.xml .
RUN mvn dependency:go-offline -Dsilent=true && mvn com.github.eirslett:frontend-maven-plugin:install-node-and-npm -DnodeVersion="v12.13.0"
COPY . .
RUN mvn -e -B package -DskipTests -Pproduction

#FROM tomee:8-jre-8.0.0-M1-plume as tomee
#WORKDIR /usr/local/tomee/webapps
#RUN rm -Rf -- *
#COPY --from=build /usr/local/app/target/*.war app.war
#CMD ["catalina.sh","run"]

FROM jboss/wildfly:18.0.1.Final
COPY --from=build /usr/local/app/target/*.war /opt/jboss/wildfly/standalone/deployments/
