/usr/java/jdk1.5.0_15/bin/java -classpath /root/java/classes:/root/java/lib/mysql-connector-java-5.0.2-beta-bin.jar com.kved.Ping &
echo "kill -9 "$! > stop.sh
chmod 777 stop.sh
