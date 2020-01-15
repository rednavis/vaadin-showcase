#!/bin/bash
if [ -z "$PORT" ]
then
  echo "Tomcat HTTP port not changed"
else
  echo "Tomcat HTTP port set to $PORT"
  sed -i "s/port=\"[0-9]\+\" protocol=\"HTTP\/1.1\"/port=\"$PORT\" protocol=\"HTTP\/1.1\"/" /usr/local/tomee/conf/server.xml
fi

echo "/usr/local/tomee/bin/catalina.sh run"
cd /usr/local/tomee/bin
sh ./catalina.sh run