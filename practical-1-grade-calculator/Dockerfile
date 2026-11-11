FROM eclipse-temurin:17-jdk AS build

WORKDIR /app
COPY src/main/java /app/src
RUN mkdir classes && javac -d classes /app/src/com/example/Mahathi/GradeCalculator.java

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/classes ./classes
ENTRYPOINT ["java", "-cp", "classes", "com.example.Mahathi.GradeCalculator"]
