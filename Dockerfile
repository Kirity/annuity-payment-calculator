FROM openjdk:8
RUN echo pwd
RUN echo ls
ADD target/annuity-plan-generator.jar annuity-plan-generator.jar
EXPOSE 9999
ENTRYPOINT ["java", "-jar", "annuity-plan-generator.jar"]