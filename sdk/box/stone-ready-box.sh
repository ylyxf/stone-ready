if [ ${1} == "start" ]; then
	/bin/sh /root/service-script/stone-ready-box-start.sh &
elif [ ${1} == "stop" ];then
	/bin/sh /root/service-script/stone-ready-box-stop.sh &
fi
