# 关闭所有正在运行容器

docker ps -a | grep "rancher" | awk '{print $1}' | xargs docker stop

docker ps | awk  '{print $1}' | xargs docker stop
# 删除所有容器应用
docker ps -a | awk  '{print $1}' | xargs docker rm

docker ps -a | grep "rancher" | awk '{print $1}' | xargs docker rm

# 或者
docker rm $(docker ps -a -q)
#删除所有镜像
docker rmi $(docker images)
#批量删除镜像
docker rmi $(docker images | grep "registry" | awk '{print $3}')
