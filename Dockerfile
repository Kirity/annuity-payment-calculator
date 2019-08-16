FROM openjdk:8
ADD target/annuity-plan-generator.jar annuity-plan-generator.jar
EXPOSE 9999
ENTRYPOINT ["java", "-jar", "annuity-plan-generator.jar"]