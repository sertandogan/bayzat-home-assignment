FROM maven:3.8.6-openjdk-18 as build
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn verify -U --no-transfer-progress -Dmaven.test.skip=true


FROM azul/zulu-openjdk-alpine:11.0.13-jre
RUN mkdir /app
COPY --from=build /project/target/*.jar /app
WORKDIR /app
EXPOSE 8081
ENTRYPOINT java -jar *.jar