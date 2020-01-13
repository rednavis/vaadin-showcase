FROM maven:3.6.3-jdk-8-slim
COPY . /usr/local/app
WORKDIR /usr/local/app
RUN mvn com.github.eirslett:frontend-maven-plugin:install-node-and-npm -DnodeVersion="v12.13.0"
CMD mvn package tomee:run