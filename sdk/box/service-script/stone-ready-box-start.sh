#!/bin/sh
if [ ! -f "/var/lib/pgsql/11/data/PG_VERSION" ];then
sed -i 's/systemctl show -p Environment "${SERVICE_NAME}.service"/cat \/usr\/lib\/systemd\/system\/${SERVICE_NAME}.service | grep Environment=PGDATA=/' /usr/pgsql-11/bin/postgresql-11-setup 
sed -i "s/\$PGSETUP_INITDB_OPTIONS/--encoding='UTF8' --locale='zh_CN.utf8'/" /usr/pgsql-11/bin/postgresql-11-setup
/bin/sh /usr/pgsql-11/bin/postgresql-11-setup initdb
sed -i "s/#listen_addresses = 'localhost'/listen_addresses = '*'/" /var/lib/pgsql/11/data/postgresql.conf
sed -i "s/host    all             all             127.0.0.1\/32            ident/host    all             all             0.0.0.0\/0            md5/"  /var/lib/pgsql/11/data/pg_hba.conf
systemctl restart postgresql-11
su -c "psql -e -c  \"ALTER USER postgres WITH PASSWORD 'postgres' \"; " - postgres
fi

nohup pgweb --bind=0.0.0.0 --listen=4201 --url postgres://postgres:postgres@127.0.0.1:5432/postgres &

if [  -f "/usr/local/lib/stone-ready/stone-ready-registry.war" ];then
nohup /usr/bin/java -jar /usr/local/lib/stone-ready/stone-ready-registry.war &
fi

if [  -f "/usr/local/lib/stone-ready/stone-ready-config.war" ];then
nohup /usr/bin/java -jar /usr/local/lib/stone-ready/stone-ready-config.war &
fi

if [  -f "/usr/local/lib/stone-ready/stone-ready-mq.war" ];then
nohup /usr/bin/java -jar /usr/local/lib/stone-ready/stone-ready-mq.war &
fi
