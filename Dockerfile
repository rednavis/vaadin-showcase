FROM maven:3.6.3-jdk-8-slim as build
WORKDIR /usr/local/app
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY . .
RUN mvn com.github.eirslett:frontend-maven-plugin:install-node-and-npm -DnodeVersion="v12.13.0" && mvn -e -B package -DskipTests -Pproduction

FROM tomee:8-jre-8.0.0-M1-plume
WORKDIR /usr/local/tomee/webapps
RUN rm -Rf *
COPY --from=build /usr/local/app/target/*.war app.war
CMD ["catalina.sh","run"]