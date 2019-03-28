#!/bin/sh
#curl -sL https://github.com/ylyxf/stone-ready/raw/develop/sdk/sdk.sh | bash -s develop
if [ -f "./sdk.sh" ]; then
 sed -i '30,$d' ./sdk.sh
 find -type f | awk  '{"dirname "$0|getline dir; print  "wget -P " dir"/"  " https://github.com/ylyxf/stone-ready/raw/$branch/sdk"substr($0,2)}' >> ./sdk.sh
 echo "chmod +x run-box.sh" >> ./sdk.sh
 echo "cd ./builder && docker build -t stone-ready-builder . && cd .." >> ./sdk.sh
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
wget -P ./box/ https://github.com/ylyxf/stone-ready/raw/$branch/sdk/box/stone-ready-box.service
wget -P ./box/ https://github.com/ylyxf/stone-ready/raw/$branch/sdk/box/stone-ready-box.sh
wget -P ./builder/ https://github.com/ylyxf/stone-ready/raw/$branch/sdk/builder/dockerfile
wget -P ./ https://github.com/ylyxf/stone-ready/raw/$branch/sdk/run-box.sh
wget -P ./ https://github.com/ylyxf/stone-ready/raw/$branch/sdk/sdk.sh
chmod +x run-box.sh
cd ./builder && docker build -t stone-ready-builder . && cd ..
