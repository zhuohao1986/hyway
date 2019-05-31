#添加docker管理软件
docker run --name=rancher-server1.6.8 -tid --restart=unless-stopped -p 8000:8080 rancher/server:latest
#添加docker管理软件rancher2.0
docker run -tid--name=rancher-server  --restart=unless-stopped   -p 8000:80 -p 8443:443   rancher/rancher:latest




