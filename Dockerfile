FROM maven:3.8.3-jdk-11-slim

MAINTAINER gsugambit@gmail.com

ENV PORT=8080

EXPOSE 8080

COPY . /code-repo
WORKDIR /code-repo
RUN mvn clean install -DskipTests=true
RUN rm /code-repo/target/*SNAPSHOT.jar
RUN cp /code-repo/target/*.jar /partydj-server.jar
RUN rm -rf /code-repo
RUN cd /

ENTRYPOINT exec java $JAVA_OPTS $SPRING_OPTS -jar /partydj-server.jar
