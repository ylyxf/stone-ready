#build stone-ready core modules
work_dir=$(pwd)
build_dir=_build$(date "+%Y-%m-%d~%H-%M-%S")
mkdir -p ./_share/lib/maven
mkdir -p ./_share/lib/npm
mkdir -p ./${build_dir}

cp ./box/build.xml ./${build_dir}/build.xml

docker run --name stone-ready-builder -it -v $work_dir/${build_dir}:/root/build -v $work_dir/_share/lib:/root/lib stone-ready-builder:latest ant -f /root/build/build.xml -Dsrc.branch=$1

docker stop stone-ready-builder
docker rm stone-ready-builder

mv ./${build_dir}/dist ./box/dist
mv ./${build_dir}/build.log ./box/dist/build.log
cd box
docker build -t stone-ready-box .
cd ..

#clear
rm -rf ./${build_dir}
rm -rf ./box/dist
#run 
docker stop stone-ready-box
docker rm stone-ready-box
docker run --name stone-ready-box -d --privileged -p80:80 -p1979:1979 -p5433:5432  -p15672:15672 -v /var/srbox/pgsql:/var/lib/pgsql/11/ stone-ready-box:latest

