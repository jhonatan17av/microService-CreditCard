FROM openjdk:8
VOLUME /tmp
EXPOSE 8686
ADD ./target/microService-CreditCard-0.0.1-SNAPSHOT.jar service-credit-card.jar
ENTRYPOINT ["java","-jar","/service-credit-card.jar"]