FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine-slim
RUN mkdir /app
WORKDIR /app
COPY ./target/applause-tester-matching-1.0-SNAPSHOT-jar-with-dependencies.jar ./app.jar
CMD java -jar ./app.jar