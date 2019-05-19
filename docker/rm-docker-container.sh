# 关闭所有正在运行容器
docker ps | awk  '{print $1}' | xargs docker stop
# 删除所有容器应用
docker ps -a | awk  '{print $1}' | xargs docker rm
# 或者
docker rm $(docker ps -a -q)