FROM tomee:11-jre-8.0.0-M3-webprofile
COPY server.xml.overide /usr/local/app/server.xml
COPY tomee_run.sh /usr/local/
RUN chmod +x /usr/local/tomee_run.sh
COPY target/vaadin-showcase-1.0-SNAPSHOT.war /usr/local/app/app.war
ENV JAVA_OPTS="${JAVA_OPTS} -Xms256m -Xmx1024m -XX:+UseContainerSupport"
CMD /usr/local/tomee_run.sh