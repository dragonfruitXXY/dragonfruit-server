#!/bin/bash

function start(){
    echo "Starting dragonfruit server..."
    num=`ps -ef | grep java | grep dragonfruit | wc -l`
    if [ ${num} -eq 0 ] ; then
    	nohup java -classpath "./lib/*:$CLASSPATH" dragonfruit.server.Main >> dragonfruit.log 2>&1 &
    	sleep 3
    	echo "Dragonfruit server started."
    	exit 0
    else
    	echo "Dragonfruit server has already started."
    	exit 0	
    fi
}

function stop(){
    echo "Stopping dragonfruit server..."
    num=`ps -ef | grep java | grep dragonfruit | wc -l`
    if [ ${num} -ne 0 ] ; then
    	pid=`ps -ef | grep java | grep dragonfruit | awk '{print $2}'`
    	kill -9 ${pid}
    	sleep 3
    	echo "Dragonfruit server stopped."
    	exit 0
    else
    	echo "Dragonfruit server has not started."
    	exit 0
    fi
}

if [ $1 = "start" ] ; then 
	start
fi

if [ $1 = "stop" ] ; then
	stop
fi