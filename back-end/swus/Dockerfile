FROM openjdk:11-jdk
VOLUME /tmp
EXPOSE 8081

ARG JAR_FILE=build/libs/swus-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul
RUN apt-get install -y tzdata
