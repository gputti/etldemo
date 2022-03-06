#!/usr/bin/bash

export etldemo=/Users/gopi/dev/workspace/etldemo/target/etldemo-0.0.1-SNAPSHOT.jar
export etldemo_jar=etldemo-0.0.1-SNAPSHOT.jar
export etldemo_PORT=8010

export HOST_HEADER="localhost"



help(){
    echo "possible commands:"
    echo "sh server.sh status|start|stop"
    echo "--"
}

waitForKill() {
   sleep 1
   comp=$1
   while [ 1 ]
   do
      pid=`ps -ef | grep ${comp} | grep -v grep | awk '{print $2}'`
      if [ ! -z "$pid" ]; then
        echo " ${comp} is not killed, so trying kill -9 ....."
        kill -9 $pid
        sleep 1
      else
        #echo "No ${comp} found."
        break
      fi
   done
}


checkStatus() {
   comp=$1
   pid=`ps -ef |  grep ${comp} | grep -v grep  | awk '{print $2}'`
   if [ ! -z "$pid" ]; then
        echo 1
    else
        echo 0
   fi
}

start () {
    checkStatus $etldemo_jar
    if [ $? -eq 1 ]; then
        echo "etldemo portal is already running"
        return
    fi
    echo "starting etldemo portal....."
    nohup java $debug_switch  -Xms1024M  -Xmx1536M -Dlog4j2.formatMsgNoLookups=true -jar $etldemo > /tmp/etldemo.log 2>&1 &
    sleep 2
}

stop () {
	comp=$etldemo_jar
	SID=`ps -ef | grep $comp | grep -v grep | awk '{print $2}'`
	if [ -z "$SID" ]; then
	    SID=`lsof -ti :$etldemo_PORT`
    fi
	if [ -z "$SID" ]; then
		echo "etldemo portal  - Not Running"
	else
		kill $SID
		waitForKill $comp
		echo "Stopped etldemo portal"

	fi
}


if [ $# -lt 1 ]; then
    action="help"
else
    action=$1
fi

if [[  $action == "help"  ]]; then
    help
    exit
fi


if [[  $action == "status"  ]]; then
    checkStatus $etldemo_jar
    if [ $? -eq 1 ]; then
        echo "etldemo portal is already running"
        return
    else
    	echo "etldemo portal  - Not Running"
    fi
    exit
fi


if [[  $action == "start"  ]]; then
    start
    checkStatus $etldemo_jar
    exit
fi

if [[  $action == "stop"  ]]; then
    stop
    checkStatus $etldemo_jar
    exit
fi

echo "Done!!!"
