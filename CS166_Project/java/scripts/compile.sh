#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
# Indicate the path of the java compiler to use
export JAVA_HOME=/usr/csshare/pkgs/jdk1.7.0_17
export PATH=$JAVA_HOME/bin:$PATH

# compile the java program
javac -d $DIR $DIR/../src/ProfNetwork.java

#run the java program
#Use your database name, port number and login
java -cp $DIR:$DIR/../lib/pg73jdbc3.jar ProfNetwork $DB_NAME $PGPORT $USER

