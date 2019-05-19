# 关闭所有正在运行容器
#启动容器，如果镜像不存在则先下载镜像，如果容器没创建则创建容器，如果容器没启动则启动
docker-compose up -d 
#停止并移除容器
docker-compose down 
#重启服务
docker-compose restart 
