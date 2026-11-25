java -classpath /home/apache/classes:/home/apache/lib/mysql-connector-java-5.0.2-beta-bin.jar com.kved.Ping &
echo "kill -9 "$! > stop.sh
chmod apache:apache stop.sh

# -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=8463
