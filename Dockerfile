# This file is a template, and might need editing before it works on your project.
FROM openjdk:8-alpine

COPY . /usr/src/myapp
WORKDIR /usr/src/myapp

RUN javac Main.java

CMD ["java", "Main"]
