FROM maven:3.6.3-jdk-8-slim as build
WORKDIR /usr/local/app
COPY pom.xml .
RUN mvn -T 2C dependency:go-offline -Dsilent=true && \
    mvn com.github.eirslett:frontend-maven-plugin:install-node-and-npm -DnodeVersion="v12.13.0"
COPY . .
RUN mvn -T 2C -e -B package -DskipTests -Pproduction

FROM tomee:11-jre-8.0.0-M3-webprofile as tomee
COPY server.xml.overide /usr/local/app/server.xml
COPY tomee_run.sh /usr/local/
RUN chmod +x /usr/local/tomee_run.sh
ENV JAVA_OPTS="${JAVA_OPTS} -Xms256m -Xmx1024m -XX:+UseContainerSupport"
COPY --from=build /usr/local/app/target/*.war /usr/local/app/app.war
CMD /usr/local/tomee_run.sh