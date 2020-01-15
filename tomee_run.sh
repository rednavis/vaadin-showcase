#!/bin/bash

echo "overide server.xml"
mv /usr/local/app/server.xml /usr/local/tomee/conf/server.xml

if [ -z "$PORT" ]
then
  echo "Tomcat HTTP port not changed"
else
  echo "Tomcat HTTP port set to $PORT"
  sed -i "s/port=\"[0-9]\+\" protocol=\"HTTP\/1.1\"/port=\"$PORT\" protocol=\"HTTP\/1.1\"/" /usr/local/tomee/conf/server.xml
fi

echo "clean webapps folder"
rm -Rf /usr/local/tomee/webapps/*

echo "move app.war to webapps"
mv /usr/local/app/app.war /usr/local/tomee/webapps/app.war

cd /usr/local/tomee/bin

echo "catalina.sh run"
sh ./catalina.sh run