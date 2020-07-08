FROM maven:3-jdk-8 as target


WORKDIR /app

COPY pom.xml .

COPY src /app/src/

#start downloading dependencies
RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never", "package"]

EXPOSE 8080

CMD ["java", "-jar", "./target/WPLACM.jar"]
