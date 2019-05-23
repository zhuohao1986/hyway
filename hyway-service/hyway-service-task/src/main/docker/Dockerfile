#Dockerfile中的ENV支持以下变量的访问：ADD、COPY、ENV、EXPOSE、FROM、LABEL、STOPSIGNAL、USER、VOLUME、WORKDIR。

#docker build -t monitor .

#docker run -d -it -P  monitor 

#FROM（指定基础镜像） 
FROM openjdk:8-jdk-alpine

#指定镜像的作者和联系方式信息。
MAINTAINER hyway "Samuel_way@163.com"

EXPOSE 9010

VOLUME /tmp

ENV JAVA_OPTS="-Xmx256m -Xms256m"
ENV CONFIG_PROFILE=test

ADD ./hyway-monitor.jar monitor.jar

RUN bash -c 'touch /monitor.jar'

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dfile.encoding=UTF8 -Duser.timezone=GMT+08 -Djava.security.egd=file:/dev/./urandom -jar /monitor.jar --spring.profiles.active=$CONFIG_PROFILE"]