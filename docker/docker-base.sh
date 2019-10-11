#添加docker管理软件
docker run --name=rancher-server1.6.8 -tid --restart=unless-stopped -p 8000:8080 rancher/server:latest
#添加docker管理软件rancher2.0
docker run -tid --name=rancher-server  --restart=unless-stopped   -p 8000:80 -p 8443:443   rancher/rancher:latest
docker run -tid --name=rancher-server  --restart=unless-stopped   -p 80:80 -p 443:443  -v /wostore/rancher/manager-node:/var/lib/rancher/  -v /root/var/log/auditlog:/var/log/auditlog  -e AUDIT_LEVEL=3  rancher/rancher
#添加docker mariadb
docker run -p 3306:3306 -v /data/mysql:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=hyway@123# --name mariadb -d --restart unless-stopped docker.io/mariadb:latest
GRANT ALL PRIVILEGES ON *.* TO 'hyway'@'%' IDENTIFIED BY 'hyway@123#' WITH GRANT OPTION;

#docker 博客软件
docker run --detach --name solo   --volume ~/skins/:/opt/solo/skins/ --env RUNTIME_DB="MYSQL"     --env JDBC_USERNAME="hyway"     --env JDBC_PASSWORD="hyway@123#"    --env JDBC_DRIVER="com.mysql.cj.jdbc.Driver"  --env JDBC_URL="jdbc:mysql://62.234.110.157:3306/solo?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC"  b3log/solo --listen_port=9200 --server_scheme=http --server_host=47.100.166.116 --server_port=9010




