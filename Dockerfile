FROM openjdk:8-jre-alpine
WORKDIR /usr/spring/starterkit
COPY ./target/springboot-starterkit-0.0.1-SNAPSHOT.jar /usr/spring/starterkit
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "springboot-starterkit-0.0.1-SNAPSHOT.jar"]


# -----------------------------------------------------------------------------------------------
# Multi stage dockerfile, uncomment following lines to enforce a maven build and then image build
# FROM openjdk:8-jdk-alpine as build
# WORKDIR /workspace/app
# COPY mvnw .
# COPY .mvn .mvn
# COPY pom.xml .
# COPY src src
# RUN ./mvnw install -DskipTests
# RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
#
# FROM openjdk:8-jdk-alpine
# VOLUME /tmp
# ARG DEPENDENCY=/workspace/app/target/dependency
# COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
# COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
# ENTRYPOINT ["java","-cp","app:app/lib/*","com.starterkit.springboot.brs.BusReservationSystemApplication"]
