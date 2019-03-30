#!/bin/sh
#curl -sL https://github.com/ylyxf/stone-ready/raw/develop/sdk/install.sh | bash -s develop
if [ -f "./install.sh" ]; then
 sed -i '30,$d' ./install.sh
 find -type f |grep -v "install.sh" | grep -v "resources"| awk  '{"dirname "$0|getline dir; print  "wget -P " dir"/"  " https://github.com/ylyxf/stone-ready/raw/$branch/sdk"substr($0,2)}' >> ./install.sh
 echo "chmod +x box.sh" >> ./install.sh
 echo "chmod +x ./builder/build-builder-image.sh" >> ./install.sh
 echo "cd ./builder && ./build-builder-image.sh && cd .." >> ./install.sh
 echo "update dir done"
 exit
fi
#prepare branch
branch=$1
if [ x$branch = x ];then
 echo "dist branch is no special , set as develop"
 branch=develop	
fi
#prepare workdir
if [ -d "./stone-ready-builder" ];then
  mv stone-ready-builder stone-ready-builder-`date +%Y-%m-%d,%H:%m:%s`  
fi
mkdir stone-ready-builder
cd stone-ready-builder






wget -P ./box/ https://github.com/ylyxf/stone-ready/raw/$branch/sdk/box/build.xml
wget -P ./box/ https://github.com/ylyxf/stone-ready/raw/$branch/sdk/box/dockerfile
wget -P ./box/service-script/ https://github.com/ylyxf/stone-ready/raw/$branch/sdk/box/service-script/stone-ready-box-start.sh
wget -P ./box/service-script/ https://github.com/ylyxf/stone-ready/raw/$branch/sdk/box/service-script/stone-ready-box-stop.sh
wget -P ./box/service-script/ https://github.com/ylyxf/stone-ready/raw/$branch/sdk/box/service-script/stone-ready-box.sh
wget -P ./box/ https://github.com/ylyxf/stone-ready/raw/$branch/sdk/box/stone-ready-box.service
wget -P ./ https://github.com/ylyxf/stone-ready/raw/$branch/sdk/box.sh
wget -P ./builder/ https://github.com/ylyxf/stone-ready/raw/$branch/sdk/builder/build-builder-image.sh
wget -P ./builder/ https://github.com/ylyxf/stone-ready/raw/$branch/sdk/builder/dockerfile
chmod +x box.sh
chmod +x ./builder/build-builder-image.sh
cd ./builder && ./build-builder-image.sh && cd ..
